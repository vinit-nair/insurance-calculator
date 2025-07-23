import React, { useState, useEffect } from 'react';
import './FormSection.css';

interface FormSectionProps {
  title: string;
  children: React.ReactNode;
  isCompleted?: boolean;
  sectionNumber?: number;
  totalSections?: number;
  icon?: string;
  description?: string;
  onComplete?: () => void;
}

const FormSection: React.FC<FormSectionProps> = ({ 
  title, 
  children, 
  isCompleted = false,
  sectionNumber = 1,
  totalSections = 6,
  icon = "📝",
  description = ""
}) => {
  const [isExpanded, setIsExpanded] = useState(true);

  const handleToggle = () => {
    setIsExpanded(!isExpanded);
  };

  return (
    <section className={`form-section ${isCompleted ? 'completed' : ''}`}>
      <div className="section-header" onClick={handleToggle}>
        <div className="section-title-group">
          <div className="section-icon">
            {isCompleted ? '✅' : icon}
          </div>
          <div className="section-title-content">
            <h3 className="section-title">
              <span className="section-number">{sectionNumber}/{totalSections}</span>
              {title}
            </h3>
            {description && (
              <p className="section-description">{description}</p>
            )}
          </div>
        </div>
        
        <div className="section-controls">
          <div className="completion-status">
            {isCompleted ? (
              <span className="status-complete">✓ Complete</span>
            ) : (
              <span className="status-pending">Pending</span>
            )}
          </div>
          
          <button 
            className="collapse-btn"
            type="button"
            aria-label={isExpanded ? 'Collapse section' : 'Expand section'}
          >
            {isExpanded ? '▼' : '▶'}
          </button>
        </div>
      </div>

      <div className={`section-content ${isExpanded ? 'expanded' : 'collapsed'}`}>
        {children}
      </div>
    </section>
  );
};

export default FormSection; 