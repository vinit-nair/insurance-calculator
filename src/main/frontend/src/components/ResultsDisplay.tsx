import React, { useState } from 'react';
import { InsuranceCalculationResponse } from '../types/InsuranceTypes';

interface ResultsDisplayProps {
  results: InsuranceCalculationResponse;
}

const ResultsDisplay: React.FC<ResultsDisplayProps> = ({ results }) => {
  const [expandedSections, setExpandedSections] = useState<{ [key: string]: boolean }>({
    recommendation: true,
    premium: false,
    methods: false,
    recommendations: false
  });

  const formatCurrency = (amount: number): string => {
    return new Intl.NumberFormat('en-GB', {
      style: 'currency',
      currency: 'GBP',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  };

  const formatPercentage = (value: number): string => {
    return `${(value * 100).toFixed(1)}%`;
  };

  const toggleSection = (section: string) => {
    setExpandedSections(prev => ({
      ...prev,
      [section]: !prev[section]
    }));
  };

  const renderBreakdown = (breakdown: { [key: string]: number } | undefined, type: string) => {
    if (!breakdown) return null;

    return (
      <div className="breakdown-container">
        <h6>Calculation Breakdown:</h6>
        <div className="breakdown-items">
          {Object.entries(breakdown).map(([key, value]) => (
            <div key={key} className="breakdown-item">
              <span className="breakdown-label">{formatBreakdownLabel(key, type)}:</span>
              <span className="breakdown-value">{formatBreakdownValue(key, value)}</span>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const formatBreakdownLabel = (key: string, type: string): string => {
    const labels: { [key: string]: string } = {
      // Income Replacement
      'annualIncome': 'Annual Income',
      'multiplier': 'Years Multiplier',
      'totalCoverage': 'Total Coverage Needed',
      
      // DIME
      'debt': 'Other Debts',
      'income': 'Income Replacement (10 years)',
      'mortgage': 'Mortgage Balance',
      'education': 'Education Fund',
      'total': 'Total DIME Coverage',
      
      // Needs Based
      'livingExpenses': 'Future Living Expenses',
      'immediateNeeds': 'Immediate Needs',
      'otherDebts': 'Other Debts',
      
      // Human Life Value
      'netAnnualIncome': 'Net Annual Income',
      'workingYears': 'Working Years Remaining',
      'discountRate': 'Discount Rate',
      'presentValue': 'Present Value'
    };
    
    return labels[key] || key.charAt(0).toUpperCase() + key.slice(1);
  };

  const formatBreakdownValue = (key: string, value: number): string => {
    if (key === 'discountRate') {
      return formatPercentage(value);
    } else if (key === 'multiplier' || key === 'workingYears') {
      return `${value} ${key === 'workingYears' ? 'years' : 'x'}`;
    } else {
      return formatCurrency(value);
    }
  };

  return (
    <div className="results-section">
      <h3>Your Personalized Life Insurance Analysis</h3>
      
      {/* Main Recommendation */}
      <div className="result-card primary-result">
        <div 
          className="card-header clickable" 
          onClick={() => toggleSection('recommendation')}
        >
          <h4>🎯 Recommended Life Insurance Coverage</h4>
          <span className={`expand-icon ${expandedSections.recommendation ? 'expanded' : ''}`}>▼</span>
        </div>
        
        {expandedSections.recommendation && (
          <div className="card-content">
            <div className="coverage-amount">
              {formatCurrency(results.recommendedCoverage)}
            </div>
            <div className="explanation-text">
              {results.explanation.split('\n').map((line, index) => (
                <p key={index} className={line.includes(':') ? 'explanation-header' : ''}>
                  {line}
                </p>
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Premium Breakdown */}
      <div className="result-card">
        <div 
          className="card-header clickable" 
          onClick={() => toggleSection('premium')}
        >
          <h4>💰 Premium Estimate & Calculation</h4>
          <span className={`expand-icon ${expandedSections.premium ? 'expanded' : ''}`}>▼</span>
        </div>
        
        {expandedSections.premium && (
          <div className="card-content">
            <div className="premium-summary">
              <div className="premium-amount">
                <span className="monthly">{formatCurrency(results.premiumEstimate.monthly)}/month</span>
                <span className="annual">{formatCurrency(results.premiumEstimate.annual)}/year</span>
              </div>
            </div>
            
            <div className="premium-explanation">
              {results.premiumEstimate.explanation.split('\n').map((line, index) => (
                <p key={index} className={line.includes(':') ? 'calculation-step' : ''}>
                  {line}
                </p>
              ))}
            </div>
          </div>
        )}
      </div>
      
      {/* Calculation Methods */}
      <div className="result-card">
        <div 
          className="card-header clickable" 
          onClick={() => toggleSection('methods')}
        >
          <h4>📊 Detailed Calculation Methods</h4>
          <span className={`expand-icon ${expandedSections.methods ? 'expanded' : ''}`}>▼</span>
        </div>
        
        {expandedSections.methods && (
          <div className="card-content">
            <div className="methods-grid">
              
              <div className="method-card">
                <div className="method-header">
                  <h5>1. Income Replacement Method</h5>
                  <div className="method-result">
                    <span className="original-amount">{formatCurrency(results.calculations.incomeReplacement.amount)}</span>
                    <span className="adjusted-amount">{formatCurrency(results.calculations.incomeReplacement.adjustedAmount)} (adjusted)</span>
                  </div>
                </div>
                <div className="method-content">
                  <div className="formula-display">
                    <strong>Formula:</strong> 
                    <code>{results.calculations.incomeReplacement.formula}</code>
                  </div>
                  <div className="method-explanation">
                    {results.calculations.incomeReplacement.explanation.split('\n').map((line, index) => (
                      <p key={index}>{line}</p>
                    ))}
                  </div>
                  {renderBreakdown(results.calculations.incomeReplacement.breakdown, 'income')}
                </div>
              </div>

              <div className="method-card">
                <div className="method-header">
                  <h5>2. DIME Method</h5>
                  <div className="method-result">
                    <span className="original-amount">{formatCurrency(results.calculations.dime.amount)}</span>
                    <span className="adjusted-amount">{formatCurrency(results.calculations.dime.adjustedAmount)} (adjusted)</span>
                  </div>
                </div>
                <div className="method-content">
                  <div className="formula-display">
                    <strong>Formula:</strong> 
                    <code>{results.calculations.dime.formula}</code>
                  </div>
                  <div className="method-explanation">
                    {results.calculations.dime.explanation.split('\n').map((line, index) => (
                      <p key={index}>{line}</p>
                    ))}
                  </div>
                  {renderBreakdown(results.calculations.dime.breakdown, 'dime')}
                </div>
              </div>

              <div className="method-card">
                <div className="method-header">
                  <h5>3. Comprehensive Needs Analysis</h5>
                  <div className="method-result">
                    <span className="original-amount">{formatCurrency(results.calculations.needsBased.amount)}</span>
                    <span className="adjusted-amount">{formatCurrency(results.calculations.needsBased.adjustedAmount)} (adjusted)</span>
                  </div>
                </div>
                <div className="method-content">
                  <div className="formula-display">
                    <strong>Formula:</strong> 
                    <code>{results.calculations.needsBased.formula}</code>
                  </div>
                  <div className="method-explanation">
                    {results.calculations.needsBased.explanation.split('\n').map((line, index) => (
                      <p key={index}>{line}</p>
                    ))}
                  </div>
                  {renderBreakdown(results.calculations.needsBased.breakdown, 'needs')}
                </div>
              </div>

              <div className="method-card">
                <div className="method-header">
                  <h5>4. Human Life Value Method</h5>
                  <div className="method-result">
                    <span className="original-amount">{formatCurrency(results.calculations.humanLifeValue.amount)}</span>
                    <span className="adjusted-amount">{formatCurrency(results.calculations.humanLifeValue.adjustedAmount)} (adjusted)</span>
                  </div>
                </div>
                <div className="method-content">
                  <div className="formula-display">
                    <strong>Formula:</strong> 
                    <code>{results.calculations.humanLifeValue.formula}</code>
                  </div>
                  <div className="method-explanation">
                    {results.calculations.humanLifeValue.explanation.split('\n').map((line, index) => (
                      <p key={index}>{line}</p>
                    ))}
                  </div>
                  {renderBreakdown(results.calculations.humanLifeValue.breakdown, 'hlv')}
                </div>
              </div>

            </div>
          </div>
        )}
      </div>
      
      {/* Personalized Recommendations */}
      <div className="result-card">
        <div 
          className="card-header clickable" 
          onClick={() => toggleSection('recommendations')}
        >
          <h4>💡 Your Personalized Recommendations</h4>
          <span className={`expand-icon ${expandedSections.recommendations ? 'expanded' : ''}`}>▼</span>
        </div>
        
        {expandedSections.recommendations && (
          <div className="card-content">
            <div className="recommendations-grid">
              {results.recommendations.map((recommendation, index) => (
                <div key={index} className="recommendation-card">
                  <div className="recommendation-header">
                    <strong>{recommendation.title}</strong>
                  </div>
                  <div className="recommendation-content">
                    {recommendation.description.split('\n').map((line, lineIndex) => (
                      <p key={lineIndex}>{line}</p>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Action Items */}
      <div className="result-card action-card">
        <h4>🚀 Next Steps</h4>
        <div className="action-items">
          <div className="action-item">
            <span className="action-number">1</span>
            <div className="action-content">
              <strong>Review Your Results</strong>
              <p>Take time to understand each calculation method and how they apply to your situation.</p>
            </div>
          </div>
          <div className="action-item">
            <span className="action-number">2</span>
            <div className="action-content">
              <strong>Compare Insurance Quotes</strong>
              <p>Get quotes from multiple insurers using your recommended coverage amount of {formatCurrency(results.recommendedCoverage)}.</p>
            </div>
          </div>
          <div className="action-item">
            <span className="action-number">3</span>
            <div className="action-content">
              <strong>Consult with Lloyds</strong>
              <p>Speak with one of our insurance specialists to discuss your specific needs and policy options.</p>
            </div>
          </div>
          <div className="action-item">
            <span className="action-number">4</span>
            <div className="action-content">
              <strong>Regular Reviews</strong>
              <p>Reassess your insurance needs every 3-5 years or after major life events.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ResultsDisplay; 