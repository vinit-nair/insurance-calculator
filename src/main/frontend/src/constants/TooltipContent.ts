import { TooltipContent } from '../types/InsuranceTypes';

export const TOOLTIP_CONTENT: TooltipContent = {
  age: "Your age affects both premiums and coverage calculations. Younger people get lower premiums and higher income multipliers. UK insurers typically offer coverage from age 18 to 80, with premiums increasing significantly after age 40.",
  
  gender: "Gender affects calculations based on UK life expectancy data. Women typically receive 15% lower premiums due to longer average lifespan. This adjustment is based on official ONS mortality statistics.",
  
  smoking: "Smoking status dramatically impacts premiums. Non-smokers get standard rates, ex-smokers (smoke-free 12+ months) pay 20% more, and current smokers pay double. Quitting can reduce premiums by 50% after 12 months.",
  
  annualIncome: "Your gross annual income before taxes drives multiple calculations. Most UK families need 10-12 times their annual income in life insurance coverage. Higher incomes may warrant higher multipliers for lifestyle maintenance.",
  
  monthlyExpenses: "Total monthly family expenses including mortgage/rent, utilities, food, transport, and entertainment. Used to calculate how many years of expenses your family would need. UK families average £2,500-£4,000 monthly.",
  
  existingCoverage: "Current life insurance from employer benefits (typically 2-4x salary) or personal policies. This amount reduces your additional coverage needs. Include all existing coverage but exclude pension benefits.",
  
  savings: "Liquid assets available to your family including ISAs, savings accounts, and investments (valued at 70%). Excludes property and pensions. UK planning recommends maintaining 6 months expenses as emergency fund.",
  
  totalDebts: "All outstanding debts including mortgage balance, credit cards, personal loans, car finance, and other obligations. This combined total ensures all debts are covered. Note: UK student loans (Plan 1/2) are typically written off on death.",
  
  funeralCosts: "Immediate costs your family will face. UK funeral costs average £3,000-£6,000, with basic services around £4,000. London and premium services cost 30-50% more. Consider prepaid plans to fix costs.",
  
  dependents: "People financially dependent on your income, including children, non-working spouse, or supported parents. Each dependent increases coverage needs. UK childcare costs £15,000+ annually per child.",
  

  
  educationFund: "Money needed for children's education. UK university costs £27,000-£36,000 per child (3 years) plus £12,000-£15,000 annual living costs. Private school adds £15,000-£40,000 yearly. Education costs rise 4-6% annually.",
  
  coveragePeriod: "Length of coverage affects premiums significantly. Term life (10-30 years) offers lower premiums for temporary needs. Whole life provides permanent coverage with cash value but costs 3x more than term insurance.",
  
  inflationProtection: "Protects against rising costs over time. UK inflation averages 2-3% annually, meaning £100k today equals £67k purchasing power in 20 years. RPI-linked coverage costs 25% more but maintains real value."
};

export const METHOD_EXPLANATIONS = {
  incomeReplacement: {
    title: "Income Replacement Method",
    description: "UK standard approach: 10-12 times annual income, adjusted for personal circumstances",
    formula: "Annual Income × Income Multiplier",
    details: [
      "Base Multiplier: 10 years (UK standard)",
      "Age Adjustment: Under 35 (+1), Over 55 (-1)",
      "Dependents: More than 2 (+1), None (-1)",
      "Final Range: 8-12 years maximum",
      "Philosophy: Replace income for family lifestyle maintenance"
    ]
  },
  
  dime: {
    title: "DIME Method",
    description: "Systematic approach covering three major financial categories",
    formula: "Total Debts + Income + Education",
    details: [
      "D - Total Debts: All outstanding debts including mortgage, loans, credit cards",
      "I - Income: 10 years of income replacement",
      "E - Education: University/school costs for children",
      "Comprehensive: Covers all major financial obligations"
    ]
  },
  
  needsBased: {
    title: "Comprehensive Needs Analysis", 
    description: "Most detailed method considering all family expenses over time",
    formula: "(Monthly Expenses × 12 × Years) + Immediate Needs + Debts + Education",
    details: [
      "Living Expenses: Monthly costs × years of support",
      "Support Period: Based on age and dependents",
      "Immediate Needs: Funeral costs + 6 months expenses",
      "All Debts: Mortgage, loans, credit cards",
      "Future Needs: Education fund for children"
    ]
  },
  
  humanLifeValue: {
    title: "Human Life Value Method",
    description: "Economic value of your life based on future earning potential",
    formula: "Present Value of Future Earnings (3% discount rate)",
    details: [
      "Annual Income: Your total annual income",
      "Working Years: Until retirement (age 65)",
      "Discount Rate: 3% (UK inflation + investment return)",
      "Present Value: Sum of discounted future earnings",
      "Academic Approach: Used for validation of other methods"
    ]
  }
};

export const PREMIUM_FACTORS = {
  age: {
    title: "Age Impact on Premiums",
    factors: {
      "Under 30": "20% discount (low mortality risk)",
      "30-39": "Standard rates (baseline pricing)", 
      "40-49": "50% premium increase (moderate risk)",
      "50-59": "150% premium increase (higher risk)",
      "60+": "300% premium increase (high risk)"
    }
  },
  
  gender: {
    title: "Gender Premium Differences",
    factors: {
      "Female": "15% discount (longer life expectancy)",
      "Male": "Standard rates (baseline pricing)"
    }
  },
  
  smoking: {
    title: "Smoking Status Premium Impact",
    factors: {
      "Non-smoker": "Standard rates (best pricing)",
      "Ex-smoker": "20% increase (must be 12+ months smoke-free)", 
      "Current smoker": "100% increase (doubles premium cost)"
    }
  },
  
  coverage: {
    title: "Coverage Type Premium Differences",
    factors: {
      "Term Life": "Standard rates (temporary coverage)",
      "Whole Life": "300% higher (permanent + cash value)"
    }
  },
  
  inflation: {
    title: "Inflation Protection Cost",
    factors: {
      "Level Coverage": "Standard rates (fixed amount)",
      "RPI-Linked": "20% higher (increases with inflation)"
    }
  }
};

export const CALCULATION_FORMULAS = {
  incomeReplacement: {
    formula: "Coverage = Annual Income × Multiplier",
    multiplierLogic: "Base: 10 years, Age <35: +1, Age >55: -1, Dependents >2: +1, No dependents: -1",
    example: "£50,000 income × 11 multiplier = £550,000 coverage"
  },
  
  dime: {
    formula: "Coverage = Debt + Income + Mortgage + Education",
    components: {
      debt: "All non-mortgage debts (credit cards, loans)",
      income: "(Your Income - Spouse Income) × 10 years", 
      mortgage: "Outstanding mortgage balance",
      education: "University costs for all children"
    },
    example: "£20k debt + £400k income + £150k mortgage + £80k education = £650,000"
  },
  
  needsBased: {
    formula: "Coverage = Living Expenses + Immediate Needs + Debts + Education",
    components: {
      livingExpenses: "Monthly Expenses × 12 × Years of Support",
      immediateNeeds: "Funeral Costs + (6 × Monthly Expenses)",
      debts: "Mortgage + Other Debts",
      education: "Education fund for children"
    },
    yearsLogic: "No dependents: max(5, 65-age), With dependents: max(55-age, 10)"
  },
  
  humanLifeValue: {
    formula: "Coverage = Present Value of Future Earnings",
    calculation: "PV = Σ(Annual Net Income ÷ (1.03)^n) for n=1 to working years",
    components: {
      netIncome: "Your Income - Spouse Income",
      workingYears: "65 - Your Age",
      discountRate: "3% (UK inflation + investment return)"
    }
  },
  
  finalRecommendation: {
    formula: "Recommended Coverage = Median of All Methods",
    approach: "Takes the middle value of four calculations for stability and balance",
    adjustment: "All amounts reduced by existing coverage + 70% of savings",
    explanation: "Median approach avoids extreme results and provides stable recommendations"
  }
}; 