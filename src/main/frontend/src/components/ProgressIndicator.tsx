import React from 'react';
import './ProgressIndicator.css';

interface ProgressStep {
  name: string;
  description: string;
  completedAt?: string;
  durationMs?: number;
  success?: boolean;
}

interface ProgressIndicatorProps {
  currentStep: number;
  totalSteps: number;
  currentStepName: string;
  currentStepDescription: string;
  progressPercentage: number;
  status: string;
  completedSteps: ProgressStep[];
  isVisible: boolean;
}

const ProgressIndicator: React.FC<ProgressIndicatorProps> = ({
  currentStep,
  totalSteps,
  currentStepName,
  currentStepDescription,
  progressPercentage,
  status,
  completedSteps,
  isVisible
}) => {
  if (!isVisible) return null;

  const steps = [
    'Initializing',
    'Fetching Data',
    'Income Analysis',
    'DIME Analysis',
    'Needs Assessment',
    'Life Value Calculation',
    'Final Analysis',
    'Complete'
  ];

  return (
    <div className="progress-overlay">
      <div className="progress-container">
        <div className="progress-header">
          <h3>🔄 Calculating Your Insurance Needs</h3>
          <p className="progress-subtitle">Using UK actuarial standards and market data</p>
        </div>

        <div className="progress-bar-container">
          <div className="progress-bar">
            <div 
              className="progress-fill"
              style={{ width: `${progressPercentage}%` }}
            />
          </div>
          <div className="progress-text">
            {Math.round(progressPercentage)}% Complete
          </div>
        </div>

        <div className="current-step">
          <div className="step-info">
            <h4>Step {currentStep} of {totalSteps}: {currentStepName}</h4>
            <p>{currentStepDescription}</p>
          </div>
          {status === 'IN_PROGRESS' && (
            <div className="loading-spinner">
              <div className="spinner"></div>
            </div>
          )}
        </div>

        <div className="step-timeline">
          {steps.slice(0, Math.min(currentStep, steps.length)).map((stepName, index) => (
            <div key={index} className={`timeline-step ${index < currentStep - 1 ? 'completed' : index === currentStep - 1 ? 'current' : 'pending'}`}>
              <div className="step-marker">
                {index < currentStep - 1 ? '✓' : index === currentStep - 1 ? '⏳' : '⏸'}
              </div>
              <div className="step-label">{stepName}</div>
            </div>
          ))}
        </div>

        {completedSteps.length > 0 && (
          <div className="completed-steps">
            <h5>Completed Steps:</h5>
            <div className="steps-list">
              {completedSteps.map((step, index) => (
                <div key={index} className="completed-step">
                  <span className="step-name">✓ {step.name}</span>
                  <span className="step-duration">
                    {step.durationMs ? `${step.durationMs}ms` : ''}
                  </span>
                </div>
              ))}
            </div>
          </div>
        )}

        {status === 'ERROR' && (
          <div className="error-message">
            ❌ Calculation failed. Please try again.
          </div>
        )}

        {status === 'COMPLETED' && (
          <div className="success-message">
            ✅ Calculation completed successfully!
          </div>
        )}
      </div>
    </div>
  );
};

export default ProgressIndicator; 