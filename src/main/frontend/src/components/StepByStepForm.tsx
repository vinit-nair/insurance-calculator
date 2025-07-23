import React from 'react';
import { FormData, FormErrors } from '../types/InsuranceTypes';
import { TOOLTIP_CONTENT } from '../constants/TooltipContent';
import InputGroup from './InputGroup';
import SelectGroup from './SelectGroup';
import './StepByStepForm.css';

interface StepByStepFormProps {
  currentStep: number;
  totalSteps: number;
  formData: FormData;
  errors: FormErrors;
  onInputChange: (name: string, value: string) => void;
  onBlur: (name: string, value: string) => void;
  onNext: () => void;
  onBack: () => void;
  onSubmit: () => void;
  isLoading: boolean;
}

const StepByStepForm: React.FC<StepByStepFormProps> = ({
  currentStep,
  totalSteps,
  formData,
  errors,
  onInputChange,
  onBlur,
  onNext,
  onBack,
  onSubmit,
  isLoading
}) => {

  const getStepContent = () => {
    switch (currentStep) {
      case 1:
        return (
          <div className="step-content">
            <h3>👤 Basic Information</h3>
            <p>Let's start with some basic details about you.</p>
            
            <InputGroup
              label="Age"
              name="age"
              type="number"
              value={formData.age}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.age}
              tooltip={TOOLTIP_CONTENT.age}
              min={18}
              max={80}
              placeholder="e.g., 35 (UK working age average)"
              required
            />

            <SelectGroup
              label="Gender"
              name="gender"
              value={formData.gender}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.gender}
              tooltip={TOOLTIP_CONTENT.gender}
              options={[
                { value: '', label: 'Select gender' },
                { value: 'male', label: 'Male' },
                { value: 'female', label: 'Female' }
              ]}
              required
            />

            <SelectGroup
              label="Smoking Status"
              name="smoking"
              value={formData.smoking}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.smoking}
              tooltip={TOOLTIP_CONTENT.smoking}
              options={[
                { value: '', label: 'Select smoking status' },
                { value: 'non-smoker', label: 'Non-smoker (never smoked or quit 2+ years ago)' },
                { value: 'smoker', label: 'Smoker (including vaping/e-cigarettes)' }
              ]}
              required
            />
          </div>
        );

      case 2:
        return (
          <div className="step-content">
            <h3>💰 Financial Details</h3>
            <p>Understanding your income and expenses helps us calculate how much coverage your family would need.</p>
            
            <InputGroup
              label="Annual Income (before tax)"
              name="annualIncome"
              type="number"
              value={formData.annualIncome}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.annualIncome}
              tooltip={TOOLTIP_CONTENT.annualIncome}
              min={0}
              placeholder="e.g., 45000 (UK average £31,000)"
              prefix="£"
              required
            />

            <InputGroup
              label="Monthly Expenses"
              name="monthlyExpenses"
              type="number"
              value={formData.monthlyExpenses}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.monthlyExpenses}
              tooltip={TOOLTIP_CONTENT.monthlyExpenses}
              min={0}
              placeholder="e.g., 2500 (mortgage, bills, food, transport)"
              prefix="£"
              required
            />
          </div>
        );

      case 3:
        return (
          <div className="step-content">
            <h3>👨‍👩‍👧‍👦 Family Information</h3>
            <p>Tell us about your family to understand their financial needs.</p>
            
            <InputGroup
              label="Number of Dependents"
              name="dependents"
              type="number"
              value={formData.dependents}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.dependents}
              tooltip={TOOLTIP_CONTENT.dependents}
              min={0}
              max={10}
              placeholder="e.g., 2 (spouse and children who rely on your income)"
              required
            />

            <InputGroup
              label="Spouse Income (if applicable)"
              name="spouseIncome"
              type="number"
              value={formData.spouseIncome}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.spouseIncome}
              tooltip={TOOLTIP_CONTENT.spouseIncome}
              min={0}
              placeholder="e.g., 35000 (leave blank if no spouse or no income)"
              prefix="£"
            />
          </div>
        );

      case 4:
        return (
          <div className="step-content">
            <h3>🏠 Debts and Obligations</h3>
            <p>We need to understand your current financial obligations.</p>
            
            <InputGroup
              label="Mortgage Balance"
              name="mortgage"
              type="number"
              value={formData.mortgage}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.mortgage}
              tooltip={TOOLTIP_CONTENT.mortgage}
              min={0}
              placeholder="e.g., 200000 (remaining mortgage balance)"
              prefix="£"
              required
            />

            <InputGroup
              label="Other Debts"
              name="otherDebts"
              type="number"
              value={formData.otherDebts}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.otherDebts}
              tooltip={TOOLTIP_CONTENT.otherDebts}
              min={0}
              placeholder="e.g., 15000 (personal loans, credit cards, car finance)"
              prefix="£"
              required
            />

            <InputGroup
              label="Education Fund Needed"
              name="educationFund"
              type="number"
              value={formData.educationFund}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.educationFund}
              tooltip={TOOLTIP_CONTENT.educationFund}
              min={0}
              placeholder="e.g., 50000 (university costs for children)"
              prefix="£"
              required
            />

            <InputGroup
              label="Funeral Costs"
              name="funeralCosts"
              type="number"
              value={formData.funeralCosts}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.funeralCosts}
              tooltip={TOOLTIP_CONTENT.funeralCosts}
              min={0}
              placeholder="e.g., 5000 (average UK funeral costs)"
              prefix="£"
              required
            />
          </div>
        );

      case 5:
        return (
          <div className="step-content">
            <h3>🛡️ Existing Coverage</h3>
            <p>Let's see what coverage you already have.</p>
            
            <InputGroup
              label="Existing Life Insurance"
              name="existingCoverage"
              type="number"
              value={formData.existingCoverage}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.existingCoverage}
              tooltip={TOOLTIP_CONTENT.existingCoverage}
              min={0}
              placeholder="e.g., 50000 (check payslips for employer benefits) - Leave blank if none"
              prefix="£"
            />
          </div>
        );

      case 6:
        return (
          <div className="step-content">
            <h3>📋 Policy Preferences</h3>
            <p>Choose your preferred policy type and features.</p>
            
            <SelectGroup
              label="Coverage Period"
              name="coveragePeriod"
              value={formData.coveragePeriod}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.coveragePeriod}
              tooltip={TOOLTIP_CONTENT.coveragePeriod}
              options={[
                { value: '', label: 'Select coverage period' },
                { value: '10', label: '10 years - Short-term debts (cheapest option)' },
                { value: '15', label: '15 years - Medium-term needs' },
                { value: '20', label: '20 years - Most popular UK choice (mortgage period)' },
                { value: '25', label: '25 years - Until children independent' },
                { value: '30', label: '30 years - Long-term protection (full child-rearing)' },
                { value: 'whole', label: 'Whole Life - Permanent coverage (3x more expensive)' }
              ]}
              required
            />

            <SelectGroup
              label="Inflation Protection"
              name="inflationProtection"
              value={formData.inflationProtection}
              onChange={onInputChange}
              onBlur={onBlur}
              error={errors.inflationProtection}
              tooltip={TOOLTIP_CONTENT.inflationProtection}
              options={[
                { value: 'no', label: 'No - Keep premiums lower (level coverage, most choose this)' },
                { value: 'yes', label: 'Yes - Protect against inflation (RPI-linked, +20% cost)' }
              ]}
            />
          </div>
        );

      default:
        return null;
    }
  };

  const getStepTitle = () => {
    const titles = [
      'Basic Information',
      'Financial Details',
      'Family Information',
      'Debts and Obligations',
      'Existing Coverage',
      'Policy Preferences'
    ];
    return titles[currentStep - 1] || '';
  };

  const validateCurrentStep = () => {
    // Basic validation for current step
    switch (currentStep) {
      case 1:
        return formData.age && formData.gender && formData.smoking;
      case 2:
        return formData.annualIncome && formData.monthlyExpenses;
      case 3:
        return formData.dependents && formData.spouseIncome;
      case 4:
        return formData.mortgage && formData.otherDebts && formData.educationFund && formData.funeralCosts;
      case 5:
        return true; // Optional field
      case 6:
        return formData.coveragePeriod;
      default:
        return false;
    }
  };

  const handleNext = () => {
    if (validateCurrentStep()) {
      if (currentStep < totalSteps) {
        onNext();
      } else {
        onSubmit();
      }
    }
  };

  return (
    <div className="step-by-step-form">
      <div className="step-container">
        {getStepContent()}
      </div>

      <div className="step-navigation">
        {currentStep > 1 && (
          <button 
            type="button" 
            className="btn-secondary" 
            onClick={onBack}
          >
            ← Back
          </button>
        )}
        
        <button 
          type="button" 
          className="btn-primary" 
          onClick={handleNext}
          disabled={isLoading || !validateCurrentStep()}
        >
          {currentStep === totalSteps ? 'Calculate My Insurance Needs' : 'Next →'}
        </button>
      </div>
    </div>
  );
};

export default StepByStepForm; 