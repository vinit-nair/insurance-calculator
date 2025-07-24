export interface InsuranceCalculationRequest {
  age: number;
  gender: 'male' | 'female';
  smoking: 'non-smoker' | 'smoker' | 'ex-smoker';
  annualIncome: number;
  monthlyExpenses: number;
  existingCoverage: number;
  savings: number;
  totalDebts: number;
  funeralCosts: number;
  dependents: number;
  educationFund: number;
  coveragePeriod: '10' | '15' | '20' | '25' | '30' | 'whole';
  inflationProtection: 'yes' | 'no';
}

export interface PremiumEstimate {
  monthly: number;
  annual: number;
  explanation: string;
}

export interface CalculationMethod {
  amount: number;
  adjustedAmount: number;
  formula: string;
  explanation: string;
  breakdown?: { [key: string]: number };
}

export interface Recommendation {
  title: string;
  description: string;
}

export interface InsuranceCalculationResponse {
  recommendedCoverage: number;
  explanation: string;
  premiumEstimate: PremiumEstimate;
  calculations: {
    incomeReplacement: CalculationMethod;
    dime: CalculationMethod;
    needsBased: CalculationMethod;
    humanLifeValue: CalculationMethod;
  };
  recommendations: Recommendation[];
}

export interface FormData {
  age: string;
  gender: string;
  smoking: string;
  annualIncome: string;
  monthlyExpenses: string;
  existingCoverage: string;
  savings: string;
  totalDebts: string;
  funeralCosts: string;
  dependents: string;
  educationFund: string;
  coveragePeriod: string;
  inflationProtection: string;
}

export interface FormErrors {
  [key: string]: string;
}

export interface TooltipContent {
  [key: string]: string;
} 