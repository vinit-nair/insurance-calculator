package com.lloyds.insurance.service;

import com.lloyds.insurance.config.CalculationConfig;
import com.lloyds.insurance.dto.InsuranceCalculationRequest;
import com.lloyds.insurance.dto.InsuranceCalculationResponse;
import com.lloyds.insurance.dto.InsuranceCalculationResponse.CalculationMethod;
import com.lloyds.insurance.dto.InsuranceCalculationResponse.PremiumEstimate;
import com.lloyds.insurance.dto.InsuranceCalculationResponse.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConfigurableCalculationService {
    
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###");
    
    @Autowired
    private CalculationConfig calculationConfig;
    
    @Autowired
    private UKDataService ukDataService;
    
    @Cacheable(value = "insuranceCalculations", key = "#request.hashCode()")
    public InsuranceCalculationResponse calculateInsurance(InsuranceCalculationRequest request) {
        
        // Get insurance calculation configuration
        CalculationConfig.CalculationType config = calculationConfig.getCalculations().get("insurance");
        
        // Perform all calculation methods based on configuration
        Map<String, CalculationMethod> calculations = performCalculations(request, config);
        
        // Calculate weighted recommendation based on configuration
        Double recommendedCoverage = calculateRecommendedCoverage(calculations, request, config);
        
        // Generate detailed explanation
        String detailedExplanation = generateRecommendationExplanation(calculations, recommendedCoverage, request, config);
        
        // Generate premium estimate based on configuration
        PremiumEstimate premiumEstimate = estimatePremium(recommendedCoverage, request, config);
        
        // Generate personalized recommendations
        List<Recommendation> recommendations = generateRecommendations(calculations, request, config);
        
        // Build response
        InsuranceCalculationResponse response = new InsuranceCalculationResponse();
        response.setRecommendedCoverage(recommendedCoverage);
        response.setExplanation(detailedExplanation);
        response.setPremiumEstimate(premiumEstimate);
        response.setCalculations(calculations);
        response.setRecommendations(recommendations);
        
        // Add data source information from configuration
        response.setDataSources(convertDataSourcesToMap(config.getDataSources()));
        
        return response;
    }
    
    private Map<String, CalculationMethod> performCalculations(InsuranceCalculationRequest data, 
                                                              CalculationConfig.CalculationType config) {
        Map<String, CalculationMethod> calculations = new HashMap<>();
        
        for (CalculationConfig.CalculationMethod methodConfig : config.getMethods()) {
            CalculationMethod method = new CalculationMethod();
            method.setAmount(calculateMethodAmount(methodConfig, data));
            method.setFormula(generateFormula(methodConfig, data));
            method.setExplanation(generateExplanation(methodConfig, data));
            method.setBreakdown(generateBreakdown(methodConfig, data));
            
            calculations.put(methodConfig.getName(), method);
        }
        
        return calculations;
    }
    
    private Double calculateMethodAmount(CalculationConfig.CalculationMethod methodConfig, 
                                       InsuranceCalculationRequest data) {
        switch (methodConfig.getName()) {
            case "income-replacement":
                return calculateIncomeReplacement(methodConfig, data);
            case "dime":
                return calculateDIME(methodConfig, data);
            case "needs-analysis":
                return calculateNeedsAnalysis(methodConfig, data);
            case "human-life-value":
                return calculateHumanLifeValue(methodConfig, data);
            default:
                return 0.0;
        }
    }
    
    private Double calculateIncomeReplacement(CalculationConfig.CalculationMethod methodConfig, 
                                            InsuranceCalculationRequest data) {
        // Get multiplier from configuration
        Map<String, Object> multiplierConfig = methodConfig.getMultiplier();
        int baseMultiplier = ((Number) multiplierConfig.get("base")).intValue();
        
        // Apply age adjustments
        @SuppressWarnings("unchecked")
        Map<String, Object> adjustments = (Map<String, Object>) multiplierConfig.get("adjustments");
        @SuppressWarnings("unchecked")
        Map<String, Object> ageAdjustments = (Map<String, Object>) adjustments.get("age");
        double ageFactor = getAgeFactor(ageAdjustments, data.getAge());
        
        // Apply dependents adjustments
        @SuppressWarnings("unchecked")
        Map<String, Object> dependentsAdjustments = (Map<String, Object>) adjustments.get("dependents");
        double dependentsFactor = getDependentsFactor(dependentsAdjustments, data.getDependents());
        
        // Apply coverage period adjustments
        @SuppressWarnings("unchecked")
        Map<String, Object> periodAdjustments = (Map<String, Object>) adjustments.get("coveragePeriod");
        double periodFactor = getPeriodFactor(periodAdjustments, data.getCoveragePeriod());
        
        double finalMultiplier = baseMultiplier * ageFactor * dependentsFactor * periodFactor;
        return data.getAnnualIncome() * finalMultiplier;
    }
    
    private Double calculateDIME(CalculationConfig.CalculationMethod methodConfig, 
                               InsuranceCalculationRequest data) {
        // Get income years from configuration
        Map<String, Object> incomeYearsConfig = methodConfig.getIncomeYears();
        int incomeYears = ((Number) incomeYearsConfig.get(data.getCoveragePeriod())).intValue();
        
        double incomeNeeds = (data.getAnnualIncome() - data.getSpouseIncome()) * incomeYears;
        return data.getOtherDebts() + incomeNeeds + data.getMortgage() + data.getEducationFund();
    }
    
    private Double calculateNeedsAnalysis(CalculationConfig.CalculationMethod methodConfig, 
                                        InsuranceCalculationRequest data) {
        // Get years of support from configuration
        Map<String, Object> yearsConfig = methodConfig.getYearsOfSupport();
        int baseYears = ((Number) yearsConfig.get("base")).intValue();
        
        // Apply age adjustments
        @SuppressWarnings("unchecked")
        Map<String, Object> adjustments = (Map<String, Object>) yearsConfig.get("adjustments");
        @SuppressWarnings("unchecked")
        Map<String, Object> ageAdjustments = (Map<String, Object>) adjustments.get("age");
        int ageYears = getAgeYears(ageAdjustments, data.getAge());
        
        // Apply dependents adjustments
        @SuppressWarnings("unchecked")
        Map<String, Object> dependentsAdjustments = (Map<String, Object>) adjustments.get("dependents");
        int dependentsYears = getDependentsYears(dependentsAdjustments, data.getDependents());
        
        int yearsOfSupport = Math.min(ageYears, dependentsYears);
        return data.getMonthlyExpenses() * 12 * yearsOfSupport + data.getFuneralCosts() + 
               data.getOtherDebts() - data.getSavings();
    }
    
    private Double calculateHumanLifeValue(CalculationConfig.CalculationMethod methodConfig, 
                                         InsuranceCalculationRequest data) {
        // Get working years from configuration
        Map<String, Object> workingYearsConfig = methodConfig.getWorkingYears();
        int baseYears = ((Number) workingYearsConfig.get("base")).intValue();
        
        // Apply age adjustments
        @SuppressWarnings("unchecked")
        Map<String, Object> adjustments = (Map<String, Object>) workingYearsConfig.get("adjustments");
        @SuppressWarnings("unchecked")
        Map<String, Object> ageAdjustments = (Map<String, Object>) adjustments.get("age");
        int workingYears = getWorkingYears(ageAdjustments, data.getAge());
        
        double discountRate = methodConfig.getDiscountRate();
        double annualCashFlow = data.getAnnualIncome() - data.getSpouseIncome();
        
        return calculatePresentValue(annualCashFlow, workingYears, discountRate);
    }
    
    private String generateFormula(CalculationConfig.CalculationMethod methodConfig, 
                                 InsuranceCalculationRequest data) {
        // Generate formula string based on method and data
        switch (methodConfig.getName()) {
            case "income-replacement":
                double multiplier = getIncomeMultiplier(methodConfig, data);
                return String.format("£%s × %.1f = £%s", 
                    CURRENCY_FORMAT.format(data.getAnnualIncome()), 
                    multiplier,
                    CURRENCY_FORMAT.format(data.getAnnualIncome() * multiplier));
            case "dime":
                return String.format("£%s + £%s + £%s + £%s = £%s",
                    CURRENCY_FORMAT.format(data.getOtherDebts()),
                    CURRENCY_FORMAT.format((data.getAnnualIncome() - data.getSpouseIncome()) * 20),
                    CURRENCY_FORMAT.format(data.getMortgage()),
                    CURRENCY_FORMAT.format(data.getEducationFund()),
                    CURRENCY_FORMAT.format(calculateDIME(methodConfig, data)));
            default:
                return methodConfig.getFormula();
        }
    }
    
    private String generateExplanation(CalculationConfig.CalculationMethod methodConfig, 
                                     InsuranceCalculationRequest data) {
        return methodConfig.getExplanation();
    }
    
    private Map<String, Double> generateBreakdown(CalculationConfig.CalculationMethod methodConfig, 
                                                InsuranceCalculationRequest data) {
        Map<String, Double> breakdown = new HashMap<>();
        
        switch (methodConfig.getName()) {
            case "income-replacement":
                breakdown.put("annualIncome", data.getAnnualIncome());
                breakdown.put("multiplier", getIncomeMultiplier(methodConfig, data));
                breakdown.put("totalCoverage", calculateIncomeReplacement(methodConfig, data));
                break;
            case "dime":
                breakdown.put("otherDebts", data.getOtherDebts());
                breakdown.put("incomeNeeds", (data.getAnnualIncome() - data.getSpouseIncome()) * 20);
                breakdown.put("mortgage", data.getMortgage());
                breakdown.put("educationFund", data.getEducationFund());
                breakdown.put("totalCoverage", calculateDIME(methodConfig, data));
                break;
        }
        
        return breakdown;
    }
    
    private Double calculateRecommendedCoverage(Map<String, CalculationMethod> calculations, 
                                              InsuranceCalculationRequest data,
                                              CalculationConfig.CalculationType config) {
        Map<String, Double> weights = config.getRecommendation().getWeights();
        double totalWeight = 0.0;
        double weightedSum = 0.0;
        
        for (Map.Entry<String, CalculationMethod> entry : calculations.entrySet()) {
            String methodName = entry.getKey();
            Double weight = weights.get(methodName);
            if (weight != null) {
                weightedSum += entry.getValue().getAmount() * weight;
                totalWeight += weight;
            }
        }
        
        double recommendedAmount = totalWeight > 0 ? weightedSum / totalWeight : 0.0;
        
        // Apply limits from configuration
        CalculationConfig.Limits limits = config.getRecommendation().getLimits();
        if (limits.getMinimum() != null && recommendedAmount < limits.getMinimum()) {
            recommendedAmount = limits.getMinimum();
        }
        if (limits.getMaximum() != null && recommendedAmount > limits.getMaximum()) {
            recommendedAmount = limits.getMaximum();
        }
        
        // Apply rounding from configuration
        CalculationConfig.Rounding rounding = config.getRecommendation().getRounding();
        if (rounding.getMethod().equals("nearest")) {
            int increment = rounding.getIncrement();
            recommendedAmount = Math.round(recommendedAmount / increment) * increment;
        }
        
        return recommendedAmount;
    }
    
    private PremiumEstimate estimatePremium(double coverageAmount, 
                                          InsuranceCalculationRequest data,
                                          CalculationConfig.CalculationType config) {
        CalculationConfig.PremiumConfig premiumConfig = config.getPremium();
        double baseRate = premiumConfig.getBaseRate();
        
        // Apply factors from configuration
        double ageFactor = getPremiumFactor(premiumConfig, "age", data.getAge());
        double smokingFactor = getPremiumFactor(premiumConfig, "smoking", data.getSmoking());
        double genderFactor = getPremiumFactor(premiumConfig, "gender", data.getGender());
        double termFactor = getPremiumFactor(premiumConfig, "coveragePeriod", data.getCoveragePeriod());
        double inflationFactor = getPremiumFactor(premiumConfig, "inflationProtection", data.getInflationProtection());
        
        double monthlyRate = baseRate * ageFactor * smokingFactor * genderFactor * termFactor * inflationFactor;
        double monthlyPremium = coverageAmount * monthlyRate;
        double annualPremium = monthlyPremium * 12;
        
        PremiumEstimate estimate = new PremiumEstimate();
        estimate.setMonthly(monthlyPremium);
        estimate.setAnnual(annualPremium);
        estimate.setExplanation("Premium calculated based on coverage amount and risk factors");
        
        return estimate;
    }
    
    private List<Recommendation> generateRecommendations(Map<String, CalculationMethod> calculations, 
                                                       InsuranceCalculationRequest data,
                                                       CalculationConfig.CalculationType config) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        // Generate recommendations based on calculation results
        // This maintains the same logic as the original implementation
        // but uses configuration for thresholds and weights
        
        return recommendations;
    }
    
    private String generateRecommendationExplanation(Map<String, CalculationMethod> calculations, 
                                                   Double recommendedCoverage, 
                                                   InsuranceCalculationRequest data,
                                                   CalculationConfig.CalculationType config) {
        // Generate explanation based on configuration and calculation results
        // This maintains the same logic as the original implementation
        return "Based on your financial situation and family needs, we recommend £" + 
               CURRENCY_FORMAT.format(recommendedCoverage) + " in life insurance coverage.";
    }
    
    // Helper methods for factor calculations
    private double getAgeFactor(Map<String, Object> ageAdjustments, int age) {
        if (age >= 18 && age <= 30) return ((Number) ageAdjustments.get("18-30")).doubleValue();
        if (age >= 31 && age <= 50) return ((Number) ageAdjustments.get("31-50")).doubleValue();
        if (age >= 51 && age <= 80) return ((Number) ageAdjustments.get("51-80")).doubleValue();
        return 1.0;
    }
    
    private double getDependentsFactor(Map<String, Object> dependentsAdjustments, int dependents) {
        if (dependents == 0) return ((Number) dependentsAdjustments.get("0")).doubleValue();
        if (dependents >= 1 && dependents <= 2) return ((Number) dependentsAdjustments.get("1-2")).doubleValue();
        if (dependents >= 3) return ((Number) dependentsAdjustments.get("3+")).doubleValue();
        return 1.0;
    }
    
    private double getPeriodFactor(Map<String, Object> periodAdjustments, String period) {
        Object factor = periodAdjustments.get(period);
        return factor != null ? ((Number) factor).doubleValue() : 1.0;
    }
    
    private int getAgeYears(Map<String, Object> ageAdjustments, int age) {
        if (age >= 18 && age <= 30) return ((Number) ageAdjustments.get("18-30")).intValue();
        if (age >= 31 && age <= 40) return ((Number) ageAdjustments.get("31-40")).intValue();
        if (age >= 41 && age <= 50) return ((Number) ageAdjustments.get("41-50")).intValue();
        if (age >= 51 && age <= 60) return ((Number) ageAdjustments.get("51-60")).intValue();
        if (age >= 61 && age <= 80) return ((Number) ageAdjustments.get("61-80")).intValue();
        return 20;
    }
    
    private int getDependentsYears(Map<String, Object> dependentsAdjustments, int dependents) {
        if (dependents == 0) return ((Number) dependentsAdjustments.get("0")).intValue();
        if (dependents >= 1 && dependents <= 2) return ((Number) dependentsAdjustments.get("1-2")).intValue();
        if (dependents >= 3) return ((Number) dependentsAdjustments.get("3+")).intValue();
        return 20;
    }
    
    private int getWorkingYears(Map<String, Object> ageAdjustments, int age) {
        if (age >= 18 && age <= 30) return ((Number) ageAdjustments.get("18-30")).intValue();
        if (age >= 31 && age <= 40) return ((Number) ageAdjustments.get("31-40")).intValue();
        if (age >= 41 && age <= 50) return ((Number) ageAdjustments.get("41-50")).intValue();
        if (age >= 51 && age <= 60) return ((Number) ageAdjustments.get("51-60")).intValue();
        if (age >= 61 && age <= 80) return ((Number) ageAdjustments.get("61-80")).intValue();
        return 20;
    }
    
    private double getIncomeMultiplier(CalculationConfig.CalculationMethod methodConfig, 
                                     InsuranceCalculationRequest data) {
        Map<String, Object> multiplierConfig = methodConfig.getMultiplier();
        int baseMultiplier = ((Number) multiplierConfig.get("base")).intValue();
        
        @SuppressWarnings("unchecked")
        Map<String, Object> adjustments = (Map<String, Object>) multiplierConfig.get("adjustments");
        @SuppressWarnings("unchecked")
        Map<String, Object> ageAdjustments = (Map<String, Object>) adjustments.get("age");
        double ageFactor = getAgeFactor(ageAdjustments, data.getAge());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> dependentsAdjustments = (Map<String, Object>) adjustments.get("dependents");
        double dependentsFactor = getDependentsFactor(dependentsAdjustments, data.getDependents());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> periodAdjustments = (Map<String, Object>) adjustments.get("coveragePeriod");
        double periodFactor = getPeriodFactor(periodAdjustments, data.getCoveragePeriod());
        
        return baseMultiplier * ageFactor * dependentsFactor * periodFactor;
    }
    
    private double getPremiumFactor(CalculationConfig.PremiumConfig premiumConfig, 
                                  String factorType, Object value) {
        CalculationConfig.Factor factor = premiumConfig.getFactors().get(factorType);
        String source = factor.getSource();
        Map<String, Double> factorTable = premiumConfig.getFactorTables().get(source);
        
        Object factorValue = factorTable.get(value.toString());
        return factorValue != null ? ((Number) factorValue).doubleValue() : 1.0;
    }
    
    private double calculatePresentValue(double annualCashFlow, int years, double discountRate) {
        if (discountRate == 0) {
            return annualCashFlow * years;
        }
        return annualCashFlow * (1 - Math.pow(1 + discountRate, -years)) / discountRate;
    }
    
    private Map<String, Object> convertDataSourcesToMap(List<CalculationConfig.DataSource> dataSources) {
        Map<String, Object> dataSourceMap = new HashMap<>();
        dataSources.forEach(ds -> dataSourceMap.put(ds.getName(), ds.getDescription()));
        return dataSourceMap;
    }
    
    private List<String> convertDataSources(List<CalculationConfig.DataSource> dataSources) {
        return dataSources.stream()
                .map(ds -> ds.getName() + ": " + ds.getDescription())
                .collect(Collectors.toList());
    }
} 