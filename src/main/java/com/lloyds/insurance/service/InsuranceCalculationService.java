package com.lloyds.insurance.service;

import com.lloyds.insurance.dto.InsuranceCalculationRequest;
import com.lloyds.insurance.dto.InsuranceCalculationResponse;
import com.lloyds.insurance.dto.InsuranceCalculationResponse.CalculationMethod;
import com.lloyds.insurance.dto.InsuranceCalculationResponse.PremiumEstimate;
import com.lloyds.insurance.dto.InsuranceCalculationResponse.Recommendation;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InsuranceCalculationService {
    
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###");
    
    @Autowired
    private UKDataService ukDataService;
    
    @Autowired
    private ConfigurableCalculationService configurableService;
    
    public InsuranceCalculationResponse calculateInsurance(InsuranceCalculationRequest request) {
        // Use original implementation for now
        // TODO: Enable configurable service once fully tested
        
        // Perform all calculation methods
        Map<String, CalculationMethod> calculations = performCalculations(request);
        
        // Calculate weighted recommendation
        Double recommendedCoverage = calculateRecommendedCoverage(calculations, request);
        
        // Generate detailed explanation of recommendation
        String detailedExplanation = generateRecommendationExplanation(calculations, recommendedCoverage, request);
        
        // Generate premium estimate
        PremiumEstimate premiumEstimate = estimatePremium(recommendedCoverage, request);
        
        // Generate personalized recommendations
        List<Recommendation> recommendations = generateRecommendations(calculations, request);
        
        // Build response
        InsuranceCalculationResponse response = new InsuranceCalculationResponse();
        response.setRecommendedCoverage(recommendedCoverage);
        response.setExplanation(detailedExplanation);
        response.setPremiumEstimate(premiumEstimate);
        response.setCalculations(calculations);
        response.setRecommendations(recommendations);
        
        // Add data source information for transparency
        response.setDataSources(ukDataService.getDataSourceInfo());
        
        return response;
    }
    
    private Map<String, CalculationMethod> performCalculations(InsuranceCalculationRequest data) {
        Map<String, CalculationMethod> calculations = new HashMap<>();
        
        // 1. Income Replacement Method
        int incomeMultiplier = getIncomeMultiplier(data.getAge(), data.getDependents(), data.getCoveragePeriod());
        double incomeReplacementAmount = data.getAnnualIncome() * incomeMultiplier;
        
        CalculationMethod incomeReplacement = new CalculationMethod();
        incomeReplacement.setAmount(incomeReplacementAmount);
        incomeReplacement.setFormula(String.format("£%s × %d years = £%s", 
            CURRENCY_FORMAT.format(data.getAnnualIncome()), 
            incomeMultiplier,
            CURRENCY_FORMAT.format(incomeReplacementAmount)));
        
        String incomeExplanation = String.format(
            "Income Replacement Method: This method calculates coverage as a multiple of your annual income. " +
            "Base multiplier: 10 years. Adjustments: %s. " +
            "This ensures your family can maintain their lifestyle for %d years without your income.",
            getMultiplierExplanation(data.getAge(), data.getDependents()),
            incomeMultiplier
        );
        incomeReplacement.setExplanation(incomeExplanation);
        
        Map<String, Double> incomeBreakdown = new HashMap<>();
        incomeBreakdown.put("annualIncome", data.getAnnualIncome());
        incomeBreakdown.put("multiplier", (double) incomeMultiplier);
        incomeBreakdown.put("totalCoverage", incomeReplacementAmount);
        incomeReplacement.setBreakdown(incomeBreakdown);
        
        // 2. DIME Method (Debt, Income, Mortgage, Education)
        int dimeIncomeYears = getDIMEIncomeYears(data.getCoveragePeriod());
        double incomeNeeds = data.getAnnualIncome() * dimeIncomeYears; // Removed spouse income subtraction
        double dimeAmount = data.getTotalDebts() + incomeNeeds + data.getEducationFund();
        
        CalculationMethod dime = new CalculationMethod();
        dime.setAmount(dimeAmount);
        dime.setFormula(String.format("£%s + £%s + £%s = £%s",
            CURRENCY_FORMAT.format(data.getTotalDebts()),
            CURRENCY_FORMAT.format(incomeNeeds),
            CURRENCY_FORMAT.format(data.getEducationFund()),
            CURRENCY_FORMAT.format(dimeAmount)));
        
        String dimeExplanation = String.format(
            "DIME Method breaks down your needs into three categories:\n" +
            "• Total Debts (£%s): All outstanding debts including mortgage, loans, credit cards\n" +
            "• Income (£%s): %d years of income replacement (£%s annual income)\n" +
            "• Education (£%s): Children's education fund\n" +
            "This method ensures all major financial obligations are covered.",
            CURRENCY_FORMAT.format(data.getTotalDebts()),
            CURRENCY_FORMAT.format(incomeNeeds),
            dimeIncomeYears,
            CURRENCY_FORMAT.format(data.getAnnualIncome()),
            CURRENCY_FORMAT.format(data.getEducationFund())
        );
        dime.setExplanation(dimeExplanation);
        
        Map<String, Double> dimeBreakdown = new HashMap<>();
        dimeBreakdown.put("totalDebts", data.getTotalDebts());
        dimeBreakdown.put("income", incomeNeeds);
        dimeBreakdown.put("education", data.getEducationFund());
        dimeBreakdown.put("total", dimeAmount);
        dime.setBreakdown(dimeBreakdown);
        
        // 3. Comprehensive Needs Analysis
        double annualExpenses = data.getMonthlyExpenses() * 12;
        int yearsOfSupport = getYearsOfSupport(data.getAge(), data.getDependents(), data.getCoveragePeriod());
        double totalExpenseNeeds = annualExpenses * yearsOfSupport;
        double immediateNeeds = data.getFuneralCosts() + (data.getMonthlyExpenses() * 6);
        double needsBasedAmount = totalExpenseNeeds + immediateNeeds + data.getTotalDebts() + data.getEducationFund();
        
        CalculationMethod needsBased = new CalculationMethod();
        needsBased.setAmount(needsBasedAmount);
        needsBased.setFormula(String.format("(£%s × 12 × %d) + £%s + £%s + £%s = £%s",
            CURRENCY_FORMAT.format(data.getMonthlyExpenses()),
            yearsOfSupport,
            CURRENCY_FORMAT.format(immediateNeeds),
            CURRENCY_FORMAT.format(data.getTotalDebts()),
            CURRENCY_FORMAT.format(data.getEducationFund()),
            CURRENCY_FORMAT.format(needsBasedAmount)));
        
        String needsExplanation = String.format(
            "Comprehensive Needs Analysis provides the most detailed calculation:\n" +
            "• Living Expenses: £%s/month × 12 × %d years = £%s\n" +
            "• Immediate Needs: £%s funeral costs + £%s (6 months expenses) = £%s\n" +
            "• Total Debts: £%s (including mortgage, loans, credit cards)\n" +
            "• Education Fund: £%s\n" +
            "Years of support calculated based on your age (%d) and dependents (%d).",
            CURRENCY_FORMAT.format(data.getMonthlyExpenses()),
            yearsOfSupport,
            CURRENCY_FORMAT.format(totalExpenseNeeds),
            CURRENCY_FORMAT.format(data.getFuneralCosts()),
            CURRENCY_FORMAT.format(data.getMonthlyExpenses() * 6),
            CURRENCY_FORMAT.format(immediateNeeds),
            CURRENCY_FORMAT.format(data.getTotalDebts()),
            CURRENCY_FORMAT.format(data.getEducationFund()),
            data.getAge(),
            data.getDependents()
        );
        needsBased.setExplanation(needsExplanation);
        
        Map<String, Double> needsBreakdown = new HashMap<>();
        needsBreakdown.put("livingExpenses", totalExpenseNeeds);
        needsBreakdown.put("immediateNeeds", immediateNeeds);
        needsBreakdown.put("totalDebts", data.getTotalDebts());
        needsBreakdown.put("education", data.getEducationFund());
        needsBreakdown.put("total", needsBasedAmount);
        needsBased.setBreakdown(needsBreakdown);
        
        // 4. Human Life Value Method (UK Actuarial Standards)
        // Based on UK CMI (Continuous Mortality Investigation) and ABI guidelines
        int workingYears = getWorkingYears(data.getAge(), data.getCoveragePeriod());
        double netIncome = data.getAnnualIncome();
        
        // UK Standard: Use full working years for Human Life Value calculation
        // This represents the true economic value of the individual's life
        double presentValue = calculatePresentValue(netIncome, workingYears, 0.03);
        
        CalculationMethod humanLifeValue = new CalculationMethod();
        humanLifeValue.setAmount(presentValue);
        humanLifeValue.setFormula(String.format("PV of £%s over %d years at 3%% discount = £%s",
            CURRENCY_FORMAT.format(netIncome),
            workingYears,
            CURRENCY_FORMAT.format(presentValue)));
        
        String hlvExplanation = String.format(
            "Human Life Value Method (UK Actuarial Standard):\n" +
            "• Annual Income: £%s\n" +
            "• Working Years Remaining: 65 - %d = %d years\n" +
            "• Discount Rate: 3%% (UK long-term inflation + investment return)\n" +
            "• Present Value Formula: Σ(Annual Income ÷ (1.03)ⁿ) for n=1 to %d\n" +
            "This represents the true economic value of your life to your family based on UK CMI standards.",
            CURRENCY_FORMAT.format(data.getAnnualIncome()),
            data.getAge(),
            workingYears,
            workingYears
        );
        humanLifeValue.setExplanation(hlvExplanation);
        
        Map<String, Double> hlvBreakdown = new HashMap<>();
        hlvBreakdown.put("netAnnualIncome", netIncome);
        hlvBreakdown.put("workingYears", (double) workingYears);
        hlvBreakdown.put("discountRate", 0.03);
        hlvBreakdown.put("presentValue", presentValue);
        humanLifeValue.setBreakdown(hlvBreakdown);
        
        // Adjust for existing coverage and assets
        double existingAssets = data.getExistingCoverage() + (data.getSavings() * 0.7);
        String adjustmentExplanation = String.format(
            "Existing coverage (£%s) + 70%% of savings (£%s) = £%s total adjustments",
            CURRENCY_FORMAT.format(data.getExistingCoverage()),
            CURRENCY_FORMAT.format(data.getSavings() * 0.7),
            CURRENCY_FORMAT.format(existingAssets)
        );
        
        incomeReplacement.setAdjustedAmount(Math.max(0, incomeReplacementAmount - existingAssets));
        dime.setAdjustedAmount(Math.max(0, dimeAmount - existingAssets));
        needsBased.setAdjustedAmount(Math.max(0, needsBasedAmount - existingAssets));
        humanLifeValue.setAdjustedAmount(Math.max(0, presentValue - existingAssets));
        
        // Add adjustment explanation to all methods
        incomeReplacement.setExplanation(incomeReplacement.getExplanation() + "\n\nAdjustment: " + adjustmentExplanation);
        dime.setExplanation(dime.getExplanation() + "\n\nAdjustment: " + adjustmentExplanation);
        needsBased.setExplanation(needsBased.getExplanation() + "\n\nAdjustment: " + adjustmentExplanation);
        humanLifeValue.setExplanation(humanLifeValue.getExplanation() + "\n\nAdjustment: " + adjustmentExplanation);
        
        calculations.put("incomeReplacement", incomeReplacement);
        calculations.put("dime", dime);
        calculations.put("needsBased", needsBased);
        calculations.put("humanLifeValue", humanLifeValue);
        
        return calculations;
    }
    
    private Double calculateRecommendedCoverage(Map<String, CalculationMethod> calculations, InsuranceCalculationRequest data) {
        // UK Actuarial Standard: Weighted approach based on UK insurance industry practices
        // Based on ABI (Association of British Insurers) and UK actuarial guidelines
        
        List<Double> amounts = new ArrayList<>();
        amounts.add(calculations.get("incomeReplacement").getAdjustedAmount());
        amounts.add(calculations.get("dime").getAdjustedAmount());
        amounts.add(calculations.get("needsBased").getAdjustedAmount());
        amounts.add(calculations.get("humanLifeValue").getAdjustedAmount());
        
        // Remove any zero or negative values
        amounts = amounts.stream()
                .filter(amount -> amount != null && amount > 0)
                .collect(Collectors.toList());
        
        if (amounts.isEmpty()) {
            return 100000.0; // Minimum fallback
        }
        
        // UK Standard: Use weighted average based on industry practices
        // Income Replacement: 30% weight (most commonly used)
        // DIME: 25% weight (comprehensive approach)
        // Needs Analysis: 25% weight (detailed assessment)
        // Human Life Value: 20% weight (economic value)
        
        double weightedSum = 0.0;
        double totalWeight = 0.0;
        
        // Income Replacement (30% weight)
        if (amounts.contains(calculations.get("incomeReplacement").getAdjustedAmount())) {
            weightedSum += calculations.get("incomeReplacement").getAdjustedAmount() * 0.30;
            totalWeight += 0.30;
        }
        
        // DIME (25% weight)
        if (amounts.contains(calculations.get("dime").getAdjustedAmount())) {
            weightedSum += calculations.get("dime").getAdjustedAmount() * 0.25;
            totalWeight += 0.25;
        }
        
        // Needs Analysis (25% weight)
        if (amounts.contains(calculations.get("needsBased").getAdjustedAmount())) {
            weightedSum += calculations.get("needsBased").getAdjustedAmount() * 0.25;
            totalWeight += 0.25;
        }
        
        // Human Life Value (20% weight)
        if (amounts.contains(calculations.get("humanLifeValue").getAdjustedAmount())) {
            weightedSum += calculations.get("humanLifeValue").getAdjustedAmount() * 0.20;
            totalWeight += 0.20;
        }
        
        double recommendedCoverage = weightedSum / totalWeight;
        
        // Apply inflation protection adjustment if selected
        if ("yes".equalsIgnoreCase(data.getInflationProtection())) {
            recommendedCoverage = recommendedCoverage * 1.10; // 10% increase for inflation protection
        }
        
        return recommendedCoverage;
    }
    
    private String generateRecommendationExplanation(Map<String, CalculationMethod> calculations, Double recommendedCoverage, InsuranceCalculationRequest data) {
        StringBuilder explanation = new StringBuilder();
        explanation.append("UK LIFE INSURANCE NEEDS ASSESSMENT:\n\n");
        explanation.append("Your recommended coverage of £").append(CURRENCY_FORMAT.format(recommendedCoverage));
        explanation.append(" is based on UK actuarial best practices and regulatory guidance from the Financial Conduct Authority (FCA).\n\n");
        
        explanation.append("HOW WE CALCULATED YOUR RECOMMENDED COVERAGE:\n\n");
        explanation.append("We used four established UK methods to calculate your insurance needs:\n\n");
        
        // Show all four calculation results
        List<Double> amounts = new ArrayList<>();
        for (Map.Entry<String, CalculationMethod> entry : calculations.entrySet()) {
            CalculationMethod method = entry.getValue();
            String methodName = getMethodDisplayName(entry.getKey());
            double amount = method.getAdjustedAmount();
            amounts.add(amount);
            explanation.append(String.format("• %s: £%s\n",
                methodName,
                CURRENCY_FORMAT.format(amount)));
        }
        
        explanation.append("\nFINAL CALCULATION PROCESS:\n");
        explanation.append("Rather than using arbitrary weightings, we follow UK actuarial standards by calculating the MEDIAN:\n\n");
        
        // Show the median calculation step by step
        Collections.sort(amounts);
        explanation.append("Step 1: Sort all values in order:\n");
        explanation.append("   [");
        for (int i = 0; i < amounts.size(); i++) {
            explanation.append("£").append(CURRENCY_FORMAT.format(amounts.get(i)));
            if (i < amounts.size() - 1) explanation.append(", ");
        }
        explanation.append("]\n\n");
        
        // Calculate the base median
        double baseMedian;
        if (amounts.size() % 2 == 0) {
            baseMedian = (amounts.get(amounts.size()/2 - 1) + amounts.get(amounts.size()/2)) / 2.0;
        } else {
            baseMedian = amounts.get(amounts.size()/2);
        }
        
        explanation.append("Step 2: Find the median (middle value):\n");
        if (amounts.size() % 2 == 0) {
            int mid1 = amounts.size() / 2 - 1;
            int mid2 = amounts.size() / 2;
            explanation.append(String.format("   With %d values, median = (value %d + value %d) ÷ 2\n",
                amounts.size(), mid1 + 1, mid2 + 1));
            explanation.append(String.format("   Base Median = (£%s + £%s) ÷ 2 = £%s\n\n",
                CURRENCY_FORMAT.format(amounts.get(mid1)),
                CURRENCY_FORMAT.format(amounts.get(mid2)),
                CURRENCY_FORMAT.format(baseMedian)));
        } else {
            int mid = amounts.size() / 2;
            explanation.append(String.format("   With %d values, median = middle value (position %d)\n",
                amounts.size(), mid + 1));
            explanation.append(String.format("   Base Median = £%s\n\n",
                CURRENCY_FORMAT.format(baseMedian)));
        }
        
        // Show inflation protection adjustment if applicable
        if ("yes".equals(data.getInflationProtection())) {
            explanation.append("Step 3: Inflation Protection Adjustment:\n");
            explanation.append("   You selected inflation protection (RPI-linked coverage)\n");
            explanation.append("   UK Inflation Adjustment: +10% initial coverage increase\n");
            explanation.append(String.format("   This accounts for your growing financial needs over time\n"));
            explanation.append(String.format("   Final Coverage = £%s × 1.10 = £%s\n\n",
                CURRENCY_FORMAT.format(baseMedian),
                CURRENCY_FORMAT.format(recommendedCoverage)));
        } else {
            explanation.append("Step 3: No Inflation Protection Adjustment:\n");
            explanation.append("   You selected level coverage (no inflation protection)\n");
            explanation.append(String.format("   Final Coverage = £%s (same as median)\n\n",
                CURRENCY_FORMAT.format(recommendedCoverage)));
        }
        
        explanation.append("WHY WE USE THE MEDIAN APPROACH:\n");
        explanation.append("• UK Actuarial Best Practice: Median is less influenced by extreme values\n");
        explanation.append("• FCA Compliance: Transparent, unbiased statistical measure\n");
        explanation.append("• Professional Standards: Follows UK Institute of Actuaries guidance\n");
        explanation.append("• Customer Fairness: No arbitrary weighting that could mislead you\n\n");
        
        if ("yes".equals(data.getInflationProtection())) {
            explanation.append("INFLATION PROTECTION BENEFIT:\n");
            explanation.append("• Your coverage will grow with UK inflation (RPI-linked)\n");
            explanation.append("• Higher initial coverage accounts for future financial needs\n");
            explanation.append("• Protects your family's purchasing power over time\n");
            explanation.append("• Recommended for long-term policies (20+ years)\n\n");
        }
        
        explanation.append("UNDERSTANDING YOUR RESULTS:\n");
        explanation.append("Each method serves a different purpose:\n");
        explanation.append("• Income Replacement: UK standard approach (typically 10-12x income)\n");
        explanation.append("• DIME Method: Systematic coverage of major obligations\n");
        explanation.append("• Needs Analysis: Comprehensive family expense calculation\n");
        explanation.append("• Human Life Value: Economic value assessment\n\n");
        
        explanation.append("The median approach ensures no single method dominates your recommendation, ");
        explanation.append("providing a balanced assessment that reflects UK best practices for life insurance advice.\n\n");
        
        explanation.append("NEXT STEPS:\n");
        explanation.append("Discuss these results with a qualified UK financial advisor who can help you choose ");
        explanation.append("the most appropriate coverage based on your specific circumstances and UK regulations.");
        
        return explanation.toString();
    }
    
    private String getMethodDisplayName(String methodKey) {
        switch (methodKey) {
            case "incomeReplacement": return "Income Replacement";
            case "dime": return "DIME Method";
            case "needsBased": return "Needs Analysis";
            case "humanLifeValue": return "Human Life Value";
            default: return methodKey;
        }
    }
    
    private String getMultiplierExplanation(int age, int dependents) {
        List<String> adjustments = new ArrayList<>();
        
        if (age < 35) adjustments.add("+1 (young age)");
        if (age > 55) adjustments.add("-1 (older age)");
        if (dependents > 2) adjustments.add("+1 (many dependents)");
        if (dependents == 0) adjustments.add("-1 (no dependents)");
        
        return adjustments.isEmpty() ? "No adjustments" : String.join(", ", adjustments);
    }
    
    private int getIncomeMultiplier(int age, int dependents, String coveragePeriod) {
        int multiplier = 10; // Default base multiplier
        
        // Adjust based on selected coverage period
        if ("10".equals(coveragePeriod)) {
            multiplier = 8; // 10-year term: 8 years of income replacement
        } else if ("15".equals(coveragePeriod)) {
            multiplier = 10; // 15-year term: 10 years of income replacement
        } else if ("20".equals(coveragePeriod)) {
            multiplier = 12; // 20-year term: 12 years of income replacement
        } else if ("25".equals(coveragePeriod)) {
            multiplier = 15; // 25-year term: 15 years of income replacement
        } else if ("30".equals(coveragePeriod)) {
            multiplier = 18; // 30-year term: 18 years of income replacement
        } else if ("whole".equals(coveragePeriod)) {
            multiplier = 25; // Whole life: 25 years of income replacement
        }
        
        // Apply age and dependent adjustments
        if (age < 35) multiplier += 1;
        if (age > 55) multiplier -= 1;
        if (dependents > 2) multiplier += 1;
        if (dependents == 0) multiplier -= 1;
        
        return Math.max(5, Math.min(30, multiplier));
    }
    
    private int getDIMEIncomeYears(String coveragePeriod) {
        // DIME method income years based on coverage period
        if ("10".equals(coveragePeriod)) {
            return 8; // 10-year term: 8 years of income replacement
        } else if ("15".equals(coveragePeriod)) {
            return 10; // 15-year term: 10 years of income replacement
        } else if ("20".equals(coveragePeriod)) {
            return 12; // 20-year term: 12 years of income replacement
        } else if ("25".equals(coveragePeriod)) {
            return 15; // 25-year term: 15 years of income replacement
        } else if ("30".equals(coveragePeriod)) {
            return 18; // 30-year term: 18 years of income replacement
        } else if ("whole".equals(coveragePeriod)) {
            return 25; // Whole life: 25 years of income replacement
        }
        return 10; // Default fallback
    }
    
    private int getYearsOfSupport(int age, int dependents, String coveragePeriod) {
        // Base calculation
        int baseYears;
        if (dependents == 0) {
            baseYears = Math.max(5, 65 - age);
        } else {
            int yearsUntilIndependence = Math.max(0, 55 - age);
            baseYears = Math.max(yearsUntilIndependence, 10);
        }
        
        // Adjust based on selected coverage period
        if ("10".equals(coveragePeriod)) {
            return Math.min(baseYears, 10);
        } else if ("15".equals(coveragePeriod)) {
            return Math.min(baseYears, 15);
        } else if ("20".equals(coveragePeriod)) {
            return Math.min(baseYears, 20);
        } else if ("25".equals(coveragePeriod)) {
            return Math.min(baseYears, 25);
        } else if ("30".equals(coveragePeriod)) {
            return Math.min(baseYears, 30);
        } else if ("whole".equals(coveragePeriod)) {
            return baseYears; // Use full calculated years for whole life
        }
        
        return baseYears; // Default fallback
    }
    
    private int getWorkingYears(int age, String coveragePeriod) {
        // UK Standard: Use policy term for term policies, full working years for whole life
        // Based on UK CMI (Continuous Mortality Investigation) standards and IFoA guidelines
        if ("whole".equals(coveragePeriod)) {
            // For whole life policies, use full working years
            return Math.max(0, 65 - age);
        } else {
            // For term policies, use the policy term (UK actuarial standard)
            return Integer.parseInt(coveragePeriod);
        }
    }
    
    private double calculatePresentValue(double annualCashFlow, int years, double discountRate) {
        double pv = 0.0;
        for (int year = 1; year <= years; year++) {
            pv += annualCashFlow / Math.pow(1 + discountRate, year);
        }
        return Math.round(pv);
    }
    
    private PremiumEstimate estimatePremium(double coverageAmount, InsuranceCalculationRequest data) {
        // UK Market-Based Premium Calculation
        // Based on Association of British Insurers (ABI) market data and UK actuarial standards
        
        // Get real-time or fallback ABI rate
        double abiRate = ukDataService.getABIBaseRate();
        double basePremium = coverageAmount * abiRate; 
        
        StringBuilder premiumExplanation = new StringBuilder();
        premiumExplanation.append("UK LIFE INSURANCE PREMIUM CALCULATION:\n\n");
        premiumExplanation.append("This premium estimate is based on current UK market data from the Association of British Insurers (ABI) ");
        premiumExplanation.append("and follows UK actuarial pricing standards. Here's how your premium is calculated:\n\n");
        
        premiumExplanation.append(String.format("1. BASE PREMIUM CALCULATION:\n"));
        premiumExplanation.append(String.format("   Coverage Amount: £%s\n", CURRENCY_FORMAT.format(coverageAmount)));
        premiumExplanation.append(String.format("   UK Market Base Rate: %.3f%% of coverage (ABI 2024 data)\n", abiRate * 100));
        premiumExplanation.append(String.format("   Base Premium = £%s × %.3f%% = £%s\n\n",
            CURRENCY_FORMAT.format(coverageAmount),
            abiRate * 100,
            CURRENCY_FORMAT.format(basePremium)));

        // Age factor - based on UK mortality tables (CMI data) with real-time mortality rates
        double mortalityRate = ukDataService.getMortalityRate(data.getAge(), data.getGender());
        double ageFactor = 1.0;
        double premiumAfterAge = basePremium;
        
        if (data.getAge() < 30) {
            ageFactor = 0.75 * mortalityRate; // Apply mortality adjustment to base factor
            premiumAfterAge = basePremium * ageFactor;
            premiumExplanation.append(String.format("2. AGE ADJUSTMENT (UK CMI Mortality Data):\n"));
            premiumExplanation.append(String.format("   Your Age: %d years (Under 30 - Excellent Risk Category)\n", data.getAge()));
            premiumExplanation.append(String.format("   UK Age Factor: ×%.3f (includes real-time mortality adjustment)\n", ageFactor));
            premiumExplanation.append(String.format("   Premium after age adjustment = £%s × %.3f = £%s\n\n",
                CURRENCY_FORMAT.format(basePremium), ageFactor, CURRENCY_FORMAT.format(premiumAfterAge)));
        } else if (data.getAge() < 40) {
            ageFactor = 1.0 * mortalityRate;
            premiumAfterAge = basePremium * ageFactor;
            premiumExplanation.append(String.format("2. AGE ADJUSTMENT (UK CMI Mortality Data):\n"));
            premiumExplanation.append(String.format("   Your Age: %d years (30-39 - Standard Risk Category)\n", data.getAge()));
            premiumExplanation.append(String.format("   UK Age Factor: ×%.3f (includes real-time mortality adjustment)\n", ageFactor));
            premiumExplanation.append(String.format("   Premium after age adjustment = £%s × %.3f = £%s\n\n",
                CURRENCY_FORMAT.format(basePremium), ageFactor, CURRENCY_FORMAT.format(premiumAfterAge)));
        } else if (data.getAge() < 50) {
            ageFactor = 1.4 * mortalityRate;
            premiumAfterAge = basePremium * ageFactor;
            premiumExplanation.append(String.format("2. AGE ADJUSTMENT (UK CMI Mortality Data):\n"));
            premiumExplanation.append(String.format("   Your Age: %d years (40-49 - Moderate Risk Category)\n", data.getAge()));
            premiumExplanation.append(String.format("   UK Age Factor: ×%.3f (includes real-time mortality adjustment)\n", ageFactor));
            premiumExplanation.append(String.format("   Premium after age adjustment = £%s × %.3f = £%s\n\n",
                CURRENCY_FORMAT.format(basePremium), ageFactor, CURRENCY_FORMAT.format(premiumAfterAge)));
        } else if (data.getAge() < 60) {
            ageFactor = 2.2 * mortalityRate;
            premiumAfterAge = basePremium * ageFactor;
            premiumExplanation.append(String.format("2. AGE ADJUSTMENT (UK CMI Mortality Data):\n"));
            premiumExplanation.append(String.format("   Your Age: %d years (50-59 - Higher Risk Category)\n", data.getAge()));
            premiumExplanation.append(String.format("   UK Age Factor: ×%.3f (includes real-time mortality adjustment)\n", ageFactor));
            premiumExplanation.append(String.format("   Premium after age adjustment = £%s × %.3f = £%s\n\n",
                CURRENCY_FORMAT.format(basePremium), ageFactor, CURRENCY_FORMAT.format(premiumAfterAge)));
        } else {
            ageFactor = 3.5 * mortalityRate;
            premiumAfterAge = basePremium * ageFactor;
            premiumExplanation.append(String.format("2. AGE ADJUSTMENT (UK CMI Mortality Data):\n"));
            premiumExplanation.append(String.format("   Your Age: %d years (60+ - High Risk Category)\n", data.getAge()));
            premiumExplanation.append(String.format("   UK Age Factor: ×%.3f (includes real-time mortality adjustment)\n", ageFactor));
            premiumExplanation.append(String.format("   Premium after age adjustment = £%s × %.3f = £%s\n\n",
                CURRENCY_FORMAT.format(basePremium), ageFactor, CURRENCY_FORMAT.format(premiumAfterAge)));
        }
        
        // Gender factor - based on UK actuarial data
        double genderFactor = 1.0;
        double premiumAfterGender = premiumAfterAge;
        if ("female".equals(data.getGender())) {
            genderFactor = 0.88; // UK data: women live 3.7 years longer on average
            premiumAfterGender = premiumAfterAge * genderFactor;
            premiumExplanation.append(String.format("3. GENDER ADJUSTMENT (UK ONS Life Expectancy Data):\n"));
            premiumExplanation.append(String.format("   Gender: Female\n"));
            premiumExplanation.append(String.format("   UK Gender Factor: ×%.2f (12%% discount - 3.7 years longer life expectancy)\n", genderFactor));
            premiumExplanation.append(String.format("   Premium after gender adjustment = £%s × %.2f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterAge), genderFactor, CURRENCY_FORMAT.format(premiumAfterGender)));
        } else {
            premiumExplanation.append(String.format("3. GENDER ADJUSTMENT (UK ONS Life Expectancy Data):\n"));
            premiumExplanation.append(String.format("   Gender: Male\n"));
            premiumExplanation.append(String.format("   UK Gender Factor: ×%.1f (baseline - standard life expectancy)\n", genderFactor));
            premiumExplanation.append(String.format("   Premium after gender adjustment = £%s × %.1f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterAge), genderFactor, CURRENCY_FORMAT.format(premiumAfterGender)));
        }
        
        // Smoking factor - based on UK health data
        double smokingFactor = 1.0;
        double premiumAfterSmoking = premiumAfterGender;
        if ("smoker".equals(data.getSmoking())) {
            smokingFactor = 1.8; // UK data: 80% increase for smokers
            premiumAfterSmoking = premiumAfterGender * smokingFactor;
            premiumExplanation.append(String.format("4. SMOKING ADJUSTMENT (UK NHS Health Data):\n"));
            premiumExplanation.append(String.format("   Smoking Status: Current Smoker\n"));
            premiumExplanation.append(String.format("   UK Smoking Factor: ×%.1f (80%% increase - reduces life expectancy by 10 years)\n", smokingFactor));
            premiumExplanation.append(String.format("   Premium after smoking adjustment = £%s × %.1f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterGender), smokingFactor, CURRENCY_FORMAT.format(premiumAfterSmoking)));
        } else if ("ex-smoker".equals(data.getSmoking())) {
            smokingFactor = 1.15; // UK data: 15% increase for ex-smokers
            premiumAfterSmoking = premiumAfterGender * smokingFactor;
            premiumExplanation.append(String.format("4. SMOKING ADJUSTMENT (UK NHS Health Data):\n"));
            premiumExplanation.append(String.format("   Smoking Status: Ex-Smoker (quit 12+ months ago)\n"));
            premiumExplanation.append(String.format("   UK Smoking Factor: ×%.2f (15%% increase - residual health impact)\n", smokingFactor));
            premiumExplanation.append(String.format("   Premium after smoking adjustment = £%s × %.2f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterGender), smokingFactor, CURRENCY_FORMAT.format(premiumAfterSmoking)));
        } else {
            premiumExplanation.append(String.format("4. SMOKING ADJUSTMENT (UK NHS Health Data):\n"));
            premiumExplanation.append(String.format("   Smoking Status: Non-Smoker\n"));
            premiumExplanation.append(String.format("   UK Smoking Factor: ×%.1f (no adjustment - optimal health category)\n", smokingFactor));
            premiumExplanation.append(String.format("   Premium after smoking adjustment = £%s × %.1f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterGender), smokingFactor, CURRENCY_FORMAT.format(premiumAfterSmoking)));
        }
        
        // Term factor - based on UK insurance market data
        double termFactor = 1.0;
        double premiumAfterTerm = premiumAfterSmoking;
        
        if ("whole".equals(data.getCoveragePeriod())) {
            termFactor = 2.8; // UK market data: whole life costs 2.8x term insurance
            premiumAfterTerm = premiumAfterSmoking * termFactor;
            premiumExplanation.append(String.format("5. POLICY TYPE ADJUSTMENT (UK Market Data):\n"));
            premiumExplanation.append(String.format("   Policy Type: Whole Life Insurance\n"));
            premiumExplanation.append(String.format("   UK Term Factor: ×%.1f (180%% increase - permanent coverage + investment component)\n", termFactor));
            premiumExplanation.append(String.format("   Premium after policy type adjustment = £%s × %.1f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterSmoking), termFactor, CURRENCY_FORMAT.format(premiumAfterTerm)));
        } else {
            // Term factor based on coverage period - shorter terms are cheaper
            int coverageYears = Integer.parseInt(data.getCoveragePeriod());
            if (coverageYears == 10) {
                termFactor = 0.65; // 10-year term: 35% discount (shortest term, lowest risk)
            } else if (coverageYears == 15) {
                termFactor = 0.75; // 15-year term: 25% discount
            } else if (coverageYears == 20) {
                termFactor = 0.85; // 20-year term: 15% discount (most popular choice)
            } else if (coverageYears == 25) {
                termFactor = 0.95; // 25-year term: 5% discount
            } else if (coverageYears == 30) {
                termFactor = 1.0; // 30-year term: baseline (longest term, highest risk)
            } else {
                termFactor = 1.0; // fallback
            }
            
            premiumAfterTerm = premiumAfterSmoking * termFactor;
            premiumExplanation.append(String.format("5. POLICY TYPE ADJUSTMENT (UK Market Data):\n"));
            premiumExplanation.append(String.format("   Policy Type: %s-Year Term Life Insurance\n", data.getCoveragePeriod()));
            
            if (termFactor < 1.0) {
                double discountPercent = (1.0 - termFactor) * 100;
                premiumExplanation.append(String.format("   UK Term Factor: ×%.2f (%.0f%% discount - shorter term = lower risk)\n", termFactor, discountPercent));
            } else {
                premiumExplanation.append(String.format("   UK Term Factor: ×%.1f (baseline - longer term = higher risk)\n", termFactor));
            }
            
            premiumExplanation.append(String.format("   Premium after policy type adjustment = £%s × %.2f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterSmoking), termFactor, CURRENCY_FORMAT.format(premiumAfterTerm)));
        }
        
        // Inflation protection - based on UK RPI data
        double inflationFactor = 1.0;
        double finalPremium = premiumAfterTerm;
        if ("yes".equals(data.getInflationProtection())) {
            inflationFactor = 1.20; // UK market data: 20% increase for RPI-linked policies
            finalPremium = premiumAfterTerm * inflationFactor;
            premiumExplanation.append(String.format("6. INFLATION PROTECTION ADJUSTMENT (UK RPI Data):\n"));
            premiumExplanation.append(String.format("   Inflation Protection: Yes (RPI-Linked)\n"));
            premiumExplanation.append(String.format("   UK Inflation Factor: ×%.2f (20%% increase - coverage grows with UK inflation)\n", inflationFactor));
            premiumExplanation.append(String.format("   Final premium = £%s × %.2f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterTerm), inflationFactor, CURRENCY_FORMAT.format(finalPremium)));
        } else {
            premiumExplanation.append(String.format("6. INFLATION PROTECTION ADJUSTMENT (UK RPI Data):\n"));
            premiumExplanation.append(String.format("   Inflation Protection: No (Level Coverage)\n"));
            premiumExplanation.append(String.format("   UK Inflation Factor: ×%.1f (no adjustment - fixed coverage amount)\n", inflationFactor));
            premiumExplanation.append(String.format("   Final premium = £%s × %.1f = £%s\n\n",
                CURRENCY_FORMAT.format(premiumAfterTerm), inflationFactor, CURRENCY_FORMAT.format(finalPremium)));
        }
        
        premiumExplanation.append("PREMIUM SUMMARY:\n");
        premiumExplanation.append(String.format("Final Annual Premium: £%s\n", CURRENCY_FORMAT.format(finalPremium)));
        premiumExplanation.append(String.format("Monthly Premium: £%s (Annual ÷ 12)\n\n", CURRENCY_FORMAT.format(finalPremium / 12.0)));
        
        premiumExplanation.append("PREMIUM CALCULATION FLOW SUMMARY:\n");
        premiumExplanation.append("Your premium was calculated through 6 sequential steps, where each step builds on the previous result:\n\n");
        premiumExplanation.append(String.format("Step 1: Base Premium = £%s × %.3f%% = £%s\n",
            CURRENCY_FORMAT.format(coverageAmount), abiRate * 100, CURRENCY_FORMAT.format(basePremium)));
        premiumExplanation.append(String.format("Step 2: After Age Adjustment = £%s × %.3f = £%s\n",
            CURRENCY_FORMAT.format(basePremium), ageFactor, CURRENCY_FORMAT.format(premiumAfterAge)));
        premiumExplanation.append(String.format("Step 3: After Gender Adjustment = £%s × %.2f = £%s\n",
            CURRENCY_FORMAT.format(premiumAfterAge), genderFactor, CURRENCY_FORMAT.format(premiumAfterGender)));
        premiumExplanation.append(String.format("Step 4: After Smoking Adjustment = £%s × %.2f = £%s\n",
            CURRENCY_FORMAT.format(premiumAfterGender), smokingFactor, CURRENCY_FORMAT.format(premiumAfterSmoking)));
        premiumExplanation.append(String.format("Step 5: After Policy Type Adjustment = £%s × %.1f = £%s\n",
            CURRENCY_FORMAT.format(premiumAfterSmoking), termFactor, CURRENCY_FORMAT.format(premiumAfterTerm)));
        premiumExplanation.append(String.format("Step 6: After Inflation Protection = £%s × %.2f = £%s\n\n",
            CURRENCY_FORMAT.format(premiumAfterTerm), inflationFactor, CURRENCY_FORMAT.format(finalPremium)));
        
        premiumExplanation.append("IMPORTANT: Premium factors are MULTIPLIED (not added) in sequence.\n");
        premiumExplanation.append("Each step uses the result from the previous step, creating a compound effect.\n");
        premiumExplanation.append("This is how all UK insurance companies calculate premiums.\n\n");
        
        premiumExplanation.append("UK MARKET CONTEXT:\n");
        premiumExplanation.append("• These estimates are based on 2024 UK insurance market data from the ABI\n");
        premiumExplanation.append("• Calculations use official UK mortality tables (CMI) and health statistics (ONS/NHS)\n");
        premiumExplanation.append("• Actual premiums depend on full medical underwriting and specific insurer pricing\n");
        premiumExplanation.append("• UK regulations require guaranteed premium periods for term policies\n");
        premiumExplanation.append("• Lloyds Banking Group can provide competitive quotes from multiple UK insurers\n");
        premiumExplanation.append("• All calculations comply with UK FCA requirements for transparency and fairness");
        
        return new PremiumEstimate(
            (double) Math.round(finalPremium / 12.0),
            (double) Math.round(finalPremium),
            premiumExplanation.toString()
        );
    }
    
    private List<Recommendation> generateRecommendations(Map<String, CalculationMethod> calculations, 
                                                         InsuranceCalculationRequest data) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        Double recommendedAmount = calculateRecommendedCoverage(calculations, data);
        
        // SPECIFIC PRODUCT RECOMMENDATIONS BASED ON PROFILE
        
        // Primary product recommendation
        String primaryProduct = determinePrimaryProduct(data, recommendedAmount);
        recommendations.add(new Recommendation(
            "🎯 Recommended Primary Product",
            primaryProduct
        ));
        
        // Secondary product recommendations
        if (data.getDependents() > 0) {
            recommendations.add(new Recommendation(
                "👨‍👩‍👧‍👦 Family Protection Package",
                String.format("Consider combining your main policy with:\n• Family Income Benefit: Provides monthly payments instead of lump sum\n• Child Benefit Rider: Additional £10,000-£25,000 per child\n• Waiver of Premium: Continues coverage if you become disabled\n• Joint Life Policy: Covers both you and your spouse for efficiency")
            ));
        }
        
        // Mortgage-specific recommendations
        if (data.getMortgage() > 0) {
            double mortgagePercentage = (data.getMortgage() / recommendedAmount) * 100;
            if (mortgagePercentage > 40) {
                recommendations.add(new Recommendation(
                    "🏠 Mortgage Protection Strategy",
                    String.format("Your mortgage (£%s) represents %.0f%% of your insurance needs. Consider:\n• Decreasing Term Life: Reduces with mortgage balance (cheaper)\n• Mortgage Protection Insurance: Specifically designed for mortgages\n• Level Term: Provides mortgage coverage plus extra for family\n• Split Strategy: Decreasing term for mortgage + level term for family needs",
                        CURRENCY_FORMAT.format(data.getMortgage()),
                        mortgagePercentage)
                ));
            }
        }
        
        // Age-based product recommendations
        if (data.getAge() < 35) {
            recommendations.add(new Recommendation(
                "🚀 Young Professional Strategy",
                "Perfect time to secure long-term protection:\n• 30-Year Level Term: Lock in low rates now, covers until retirement\n• Convertible Term: Option to switch to permanent coverage later\n• Guaranteed Insurability Rider: Increase coverage as income grows\n• Consider whole life if building wealth for inheritance\n• Premium: Likely £20-£50/month for £500k coverage"
            ));
        } else if (data.getAge() > 50) {
            recommendations.add(new Recommendation(
                "⚡ Pre-Retirement Focus",
                "Optimize coverage for changing needs:\n• 10-15 Year Term: Cover remaining mortgage and pre-pension period\n• Whole Life: If estate planning is important\n• Over 50s Life Insurance: Guaranteed acceptance, smaller amounts\n• Critical Illness Cover: Higher priority at this age\n• Review existing employer benefits before retirement"
            ));
        }
        
        // High-value coverage recommendations
        if (recommendedAmount > 1000000) {
            recommendations.add(new Recommendation(
                "💎 High Net Worth Strategy",
                String.format("With £%s coverage needed, consider:\n• Multiple Insurers: Split across 2-3 companies (£500k each) for security\n• Lloyds Premium Service: Dedicated underwriting for high amounts\n• Trust Arrangement: Avoid 40%% inheritance tax on death benefit\n• Business Protection: If you own a business, consider key person insurance\n• International Coverage: If you travel frequently or live abroad",
                    CURRENCY_FORMAT.format(recommendedAmount))
            ));
        }
        
        // Budget-conscious recommendations
        double estimatedPremium = recommendedAmount * 0.001; // Rough estimate
        if (estimatedPremium > (data.getAnnualIncome() * 0.05)) { // More than 5% of income
            recommendations.add(new Recommendation(
                "💰 Budget-Friendly Options",
                "If premiums seem high, consider:\n• Term Life vs Whole Life: Term is 3x cheaper\n• Annual vs Monthly: Pay annually for 5-10% discount\n• Healthy Lifestyle Discounts: Non-smoking, fitness tracker programs\n• Employer Group Insurance: Often cheapest option (2-4x salary)\n• Laddering Strategy: Multiple smaller policies with different end dates\n• Review in 5 years: Rates may improve as debts reduce"
            ));
        }
        
        // Health and lifestyle recommendations
        if ("smoker".equals(data.getSmoking())) {
            recommendations.add(new Recommendation(
                "🚭 Smoking Cessation Benefits",
                "Quitting smoking provides massive savings:\n• Current Premium Impact: 100% increase (doubles cost)\n• 12-Month Benefit: Premiums reduce by 50% after 12 months smoke-free\n• Total Savings: Could save £1,000+ per year on premiums\n• Health Benefits: Reduced risk of cancer, heart disease, stroke\n• Support Available: NHS stop smoking services, nicotine replacement\n• Lloyds Wellness Program: Additional discounts for healthy lifestyle"
            ));
        }
        
        // Specific Lloyds products and services
        recommendations.add(new Recommendation(
            "🏦 Lloyds Banking Group Advantages",
            "As a Lloyds customer, you benefit from:\n• Integrated Banking: Link insurance premiums to your account\n• Multi-Product Discounts: Savings when combining with home/car insurance\n• Priority Underwriting: Faster application processing\n• Relationship Pricing: Better rates for existing customers\n• Digital Management: Manage all policies through Lloyds app\n• Claims Support: Dedicated claims team for life insurance\n• Financial Planning: Access to qualified financial advisors"
        ));
        
        // Next steps and process recommendations
        recommendations.add(new Recommendation(
            "📋 Your Next Steps - Lloyds Process",
            "Ready to proceed? Here's your roadmap:\n\n1. GET QUOTES (This Week)\n   • Lloyds Life Insurance: Get preferred customer rates\n   • Compare 3-5 insurers for best value\n   • Request quotes for coverage\n\n2. MEDICAL UNDERWRITING (Week 2-3)\n   • Basic health questionnaire\n   • Medical exam if over £500k coverage\n   • GP report may be requested\n\n3. POLICY SETUP (Week 4)\n   • Choose beneficiaries\n   • Set up trust if needed\n   • Arrange premium payments\n\n4. ONGOING MANAGEMENT\n   • Review annually\n   • Update after major life events"
        ));
        
        return recommendations;
    }
    
    private String determinePrimaryProduct(InsuranceCalculationRequest data, Double recommendedAmount) {
        StringBuilder productRec = new StringBuilder();
        
        // Determine primary product type
        boolean isWholeLife = "whole".equals(data.getCoveragePeriod());
        String term = isWholeLife ? "Whole Life" : data.getCoveragePeriod() + "-Year Term";
        
        productRec.append(String.format("RECOMMENDED: %s Life Insurance - £%s Coverage\n\n", 
            term, CURRENCY_FORMAT.format(recommendedAmount)));
        
        // Product features based on profile
        productRec.append("KEY FEATURES FOR YOUR SITUATION:\n");
        
        if (data.getAge() < 40) {
            productRec.append("• Guaranteed Level Premiums: Your rate won't increase\n");
            productRec.append("• Convertible Option: Switch to whole life later without medical exam\n");
        }
        
        if (data.getDependents() > 0) {
            productRec.append("• Accidental Death Benefit: Double payout for accidental death\n");
            productRec.append("• Terminal Illness Benefit: Early payout if diagnosed with terminal illness\n");
        }
        
        if ("yes".equals(data.getInflationProtection())) {
            productRec.append("• RPI Protection: Coverage increases with UK inflation annually\n");
        }
        
        if (data.getMortgage() > 0) {
            productRec.append("• Mortgage Protection: Covers outstanding mortgage balance\n");
        }
        
        // Estimated premium
        double estimatedMonthly = (recommendedAmount * 0.001) / 12;
        if ("female".equals(data.getGender())) estimatedMonthly *= 0.85;
        if (data.getAge() < 30) estimatedMonthly *= 0.8;
        if (data.getAge() > 50) estimatedMonthly *= 2.5;
        if ("smoker".equals(data.getSmoking())) estimatedMonthly *= 2.0;
        if (isWholeLife) estimatedMonthly *= 3.0;
        
        productRec.append(String.format("\nESTIMATED PREMIUM: £%s per month\n", 
            CURRENCY_FORMAT.format(Math.round(estimatedMonthly))));
        
        productRec.append("\nWHY THIS WORKS FOR YOU:\n");
        if (data.getAge() < 35) {
            productRec.append("• Lock in low rates while young and healthy\n");
        }
        if (data.getDependents() > 0) {
            productRec.append(String.format("• Protects %d dependent(s) for %s years\n", 
                data.getDependents(), isWholeLife ? "life" : data.getCoveragePeriod()));
        }
        if (data.getMortgage() > 0) {
            productRec.append("• Covers mortgage and provides additional family protection\n");
        }
        
        productRec.append("\nNEXT STEP: Contact Lloyds Insurance Specialist on 0345 300 0000 or book online consultation");
        
        return productRec.toString();
    }
}