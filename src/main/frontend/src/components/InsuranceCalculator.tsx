import React, { useState } from 'react';
import { FormData, FormErrors, InsuranceCalculationResponse } from '../types/InsuranceTypes';
import InsuranceService from '../services/InsuranceService';
import ResultsDisplay from './ResultsDisplay';
import ProgressIndicator from './ProgressIndicator';
import GameificationSystem from './GameificationSystem';
import InsuranceGlossary from './InsuranceGlossary';
import StepByStepForm from './StepByStepForm';
import './InsuranceCalculator.css';

interface ProgressState {
  sessionId: string;
  currentStep: number;
  totalSteps: number;
  currentStepName: string;
  currentStepDescription: string;
  progressPercentage: number;
  status: string;
  completedSteps: Array<{
    name: string;
    description: string;
    durationMs: number;
    success: boolean;
  }>;
  isVisible: boolean;
}

const InsuranceCalculator: React.FC = () => {
  // Form state
  const [formData, setFormData] = useState<FormData>({
    age: '',
    gender: '',
    smoking: '',
    annualIncome: '',
    monthlyExpenses: '',
    existingCoverage: '',
    savings: '',
    mortgage: '',
    otherDebts: '',
    funeralCosts: '',
    dependents: '',
    spouseIncome: '',
    educationFund: '',
    coveragePeriod: '',
    inflationProtection: 'no'
  });

  const [errors, setErrors] = useState<FormErrors>({});
  const [results, setResults] = useState<InsuranceCalculationResponse | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [alertMessage, setAlertMessage] = useState<{ type: 'success' | 'error'; message: string } | null>(null);
  
  // Calculator step state
  const [currentStep, setCurrentStep] = useState(1);
  const totalSteps = 6;
  
  // Glossary visibility state
  const [isGlossaryVisible, setIsGlossaryVisible] = useState(false);
  
  // Progress state
  const [progressState, setProgressState] = useState<ProgressState>({
    sessionId: '',
    currentStep: 1,
    totalSteps: 6,
    currentStepName: 'Basic Information',
    currentStepDescription: 'Enter your personal details',
    progressPercentage: 16.67,
    status: 'in-progress',
    completedSteps: [],
    isVisible: false
  });

  const validateField = (name: string, value: string): string => {
    switch (name) {
      case 'age':
        if (!value) return 'Age is required';
        const age = parseInt(value);
        if (isNaN(age) || age < 18 || age > 80) return 'Age must be between 18 and 80';
        break;
      case 'gender':
        if (!value) return 'Gender is required';
        break;
      case 'smoking':
        if (!value) return 'Smoking status is required';
        break;
      case 'annualIncome':
        if (!value) return 'Annual income is required';
        if (parseFloat(value) <= 0) return 'Annual income must be positive';
        break;
      case 'monthlyExpenses':
        if (!value) return 'Monthly expenses are required';
        if (parseFloat(value) <= 0) return 'Monthly expenses must be positive';
        break;
      case 'dependents':
        if (!value) return 'Number of dependents is required';
        const deps = parseInt(value);
        if (isNaN(deps) || deps < 0) return 'Number of dependents must be 0 or more';
        break;
      case 'mortgage':
        if (!value) return 'Mortgage amount is required';
        if (parseFloat(value) < 0) return 'Mortgage amount cannot be negative';
        break;
      case 'otherDebts':
        if (!value) return 'Other debts amount is required';
        if (parseFloat(value) < 0) return 'Other debts cannot be negative';
        break;
      case 'educationFund':
        if (!value) return 'Education fund amount is required';
        if (parseFloat(value) < 0) return 'Education fund cannot be negative';
        break;
      case 'funeralCosts':
        if (!value) return 'Funeral costs are required';
        if (parseFloat(value) < 0) return 'Funeral costs cannot be negative';
        break;
      case 'coveragePeriod':
        if (!value) return 'Coverage period is required';
        break;
    }
    return '';
  };

  const handleInputChange = (name: string, value: string) => {
    setFormData(prev => ({ ...prev, [name]: value }));
    
    // Clear error when user starts typing
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  const handleBlur = (name: string, value: string) => {
    const error = validateField(name, value);
    setErrors(prev => ({ ...prev, [name]: error }));
  };

  const validateForm = (): boolean => {
    const newErrors: FormErrors = {};
    Object.keys(formData).forEach(key => {
      const error = validateField(key, formData[key as keyof FormData]);
      if (error) newErrors[key] = error;
    });
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      setAlertMessage({ type: 'error', message: 'Please fix the errors in the form.' });
      return;
    }

    setIsLoading(true);
    setAlertMessage(null);

    try {
      // Convert form data to request format
      const request = {
        age: parseInt(formData.age),
        gender: formData.gender as 'male' | 'female',
        smoking: formData.smoking as 'non-smoker' | 'smoker' | 'ex-smoker',
        annualIncome: parseFloat(formData.annualIncome),
        monthlyExpenses: parseFloat(formData.monthlyExpenses),
        existingCoverage: parseFloat(formData.existingCoverage) || 0,
        savings: parseFloat(formData.savings) || 0,
        mortgage: parseFloat(formData.mortgage),
        otherDebts: parseFloat(formData.otherDebts),
        funeralCosts: parseFloat(formData.funeralCosts),
        dependents: parseInt(formData.dependents),
        spouseIncome: parseFloat(formData.spouseIncome) || 0,
        educationFund: parseFloat(formData.educationFund),
        coveragePeriod: formData.coveragePeriod as '10' | '15' | '20' | '25' | '30' | 'whole',
        inflationProtection: formData.inflationProtection as 'yes' | 'no'
      };

      // Start progress simulation
      simulateProgress();

      const response = await InsuranceService.calculateInsurance(request);
      setResults(response);
      setAlertMessage({ type: 'success', message: 'Calculation completed successfully!' });
      
    } catch (error: any) {
      setAlertMessage({ 
        type: 'error', 
        message: error.message || 'An error occurred while calculating your insurance needs.' 
      });
    } finally {
      setIsLoading(false);
    }
  };

  const simulateProgress = () => {
    // Reset progress state completely for each new calculation
    setProgressState(prev => ({
      ...prev,
      isVisible: true,
      status: 'in-progress',
      currentStep: 1,
      progressPercentage: 0,
      completedSteps: [] // Reset completed steps to empty array
    }));

    let currentStep = 1;
    const totalSteps = 6;
    const stepNames = [
      'Basic Information',
      'Financial Details', 
      'Family Information',
      'Debts and Obligations',
      'Existing Coverage',
      'Policy Preferences'
    ];

    const updateProgress = () => {
      if (currentStep <= totalSteps) {
        setProgressState(prev => ({
          ...prev,
          currentStep,
          currentStepName: stepNames[currentStep - 1],
          currentStepDescription: `Processing ${stepNames[currentStep - 1].toLowerCase()}`,
          progressPercentage: (currentStep / totalSteps) * 100,
          completedSteps: stepNames.slice(0, currentStep).map(name => ({
            name,
            description: `Completed ${name.toLowerCase()}`,
            durationMs: 800,
            success: true
          }))
        }));
        currentStep++;
        setTimeout(updateProgress, 800);
      } else {
        setProgressState(prev => ({
          ...prev,
          status: 'completed',
          currentStepName: 'Calculation Complete',
          currentStepDescription: 'Your insurance analysis is ready!'
        }));
        
        // Hide progress indicator after 2 seconds
        setTimeout(() => {
          setProgressState(prev => ({ ...prev, isVisible: false }));
        }, 2000);
      }
    };

    updateProgress();
  };

  // Removed unused isSectionCompleted function

  const handleSectionComplete = (sectionId: string) => {
    console.log(`Section ${sectionId} completed!`);
  };

  const handleNextStep = () => {
    if (currentStep < totalSteps) {
      setCurrentStep(currentStep + 1);
    }
  };

  const handleBackStep = () => {
    if (currentStep > 1) {
      setCurrentStep(currentStep - 1);
    }
  };

  const handleStepSubmit = async () => {
    await handleSubmit(new Event('submit') as any);
  };

  return (
    <div className="insurance-calculator">
      <header className="header">
        <div className="container">
          <h1>Life Insurance Calculator</h1>
          <p className="subtitle">Calculate your life insurance needs with our comprehensive UK-focused calculator</p>
        </div>
      </header>

      <main className="main">
        <div className="container">
          {/* Calculator Tiles */}
          <div className="calculator-tiles">
            <div className="tile calculator-tile">
              <div className="tile-header">
                <div className="tile-icon">📊</div>
                <div className="tile-content">
                  <h3>Insurance Calculator</h3>
                  <p>Step {currentStep} of {totalSteps} - {Math.round((currentStep / totalSteps) * 100)}% complete</p>
                </div>
                <div className="tile-status active">
                  <span>Step {currentStep}</span>
                </div>
              </div>
            </div>

            <div className="tile glossary-tile">
              <div className="tile-header" onClick={() => setIsGlossaryVisible(!isGlossaryVisible)}>
                <div className="tile-icon">📚</div>
                <div className="tile-content">
                  <h3>Insurance Glossary</h3>
                  <p>Learn about insurance terms and concepts</p>
                </div>
                <div className="tile-status">
                  <button className="toggle-btn">
                    {isGlossaryVisible ? '−' : '+'}
                  </button>
                </div>
              </div>
              
              {isGlossaryVisible && (
                <div className="tile-content-expanded">
                  <InsuranceGlossary />
                </div>
              )}
            </div>
          </div>
          
          {alertMessage && (
            <div className={`alert ${alertMessage.type}`}>
              {alertMessage.message}
            </div>
          )}

          {/* Progress Bar */}
          <div className="progress-bar">
            <div 
              className="progress-fill" 
              style={{ width: `${(currentStep / totalSteps) * 100}%` }}
            ></div>
            <div className="progress-text">
              {Math.round((currentStep / totalSteps) * 100)}% complete
            </div>
          </div>

          <StepByStepForm
            currentStep={currentStep}
            totalSteps={totalSteps}
            formData={formData}
            errors={errors}
            onInputChange={handleInputChange}
            onBlur={handleBlur}
            onNext={handleNextStep}
            onBack={handleBackStep}
            onSubmit={handleStepSubmit}
            isLoading={isLoading}
          />
          
          {/* Results Display */}
          {results && (
            <div id="results">
              <ResultsDisplay results={results} />
            </div>
          )}
        </div>
      </main>

      {/* Gamification System */}
      <GameificationSystem
        formData={formData}
        currentSection="basic"
        onSectionComplete={handleSectionComplete}
      />

      {/* Progress Indicator */}
      <ProgressIndicator
        currentStep={progressState.currentStep}
        totalSteps={progressState.totalSteps}
        currentStepName={progressState.currentStepName}
        currentStepDescription={progressState.currentStepDescription}
        progressPercentage={progressState.progressPercentage}
        status={progressState.status}
        completedSteps={progressState.completedSteps}
        isVisible={progressState.isVisible}
      />

      <footer className="footer">
        <div className="container">
          <p>&copy; 2024 Lloyds Banking Group. This calculator provides estimates for guidance only. Please consult with our insurance specialists for personalized advice and quotes.</p>
        </div>
      </footer>
    </div>
  );
};

export default InsuranceCalculator; 