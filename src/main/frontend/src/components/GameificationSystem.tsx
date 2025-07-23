import React, { useState, useEffect, useCallback } from 'react';
import './GameificationSystem.css';

interface CompletionTicket {
  id: string;
  title: string;
  description: string;
  icon: string;
  sectionId: string;
  isCompleted: boolean;
  completedAt?: Date;
}

interface GameificationSystemProps {
  formData: any;
  currentSection: string;
  onSectionComplete: (sectionId: string) => void;
}

const COMPLETION_TICKETS: CompletionTicket[] = [
  {
    id: 'basic-info',
    title: 'Personal Details',
    description: 'Basic information completed',
    icon: '👤',
    sectionId: 'basic',
    isCompleted: false
  },
  {
    id: 'financial-details',
    title: 'Financial Information',
    description: 'Income and expenses recorded',
    icon: '💰',
    sectionId: 'financial',
    isCompleted: false
  },
  {
    id: 'family-details',
    title: 'Family Information',
    description: 'Dependents and spouse details',
    icon: '👨‍👩‍👧‍👦',
    sectionId: 'family',
    isCompleted: false
  },
  {
    id: 'financial-needs',
    title: 'Future Expenses',
    description: 'Debts and goals outlined',
    icon: '🎯',
    sectionId: 'needs',
    isCompleted: false
  },
  {
    id: 'existing-coverage',
    title: 'Current Coverage',
    description: 'Existing insurance noted',
    icon: '🛡️',
    sectionId: 'coverage',
    isCompleted: false
  },
  {
    id: 'policy-preferences',
    title: 'Policy Preferences',
    description: 'Coverage options selected',
    icon: '📋',
    sectionId: 'policy',
    isCompleted: false
  }
];

const GameificationSystem: React.FC<GameificationSystemProps> = ({
  formData,
  currentSection,
  onSectionComplete
}) => {
  const [tickets, setTickets] = useState<CompletionTicket[]>(COMPLETION_TICKETS);
  const [showTickets, setShowTickets] = useState(false);

  const checkCompletedSections = useCallback(() => {
    const completed = [];
    
    // Only mark as complete if all required fields are filled AND not just placeholder values
    // Basic Information
    if (formData.age && formData.age !== '' && 
        formData.gender && formData.gender !== '' && 
        formData.smoking && formData.smoking !== '') {
      completed.push('basic');
    }
    
    // Financial Details
    if (formData.annualIncome && formData.annualIncome !== '' && 
        formData.monthlyExpenses && formData.monthlyExpenses !== '' && 
        formData.savings && formData.savings !== '') {
      completed.push('financial');
    }
    
    // Family Details
    if ((formData.dependents && formData.dependents !== '') || 
        (formData.spouseIncome && formData.spouseIncome !== '')) {
      completed.push('family');
    }
    
    // Financial Needs
    if ((formData.mortgage && formData.mortgage !== '') || 
        (formData.otherDebts && formData.otherDebts !== '') || 
        (formData.educationFund && formData.educationFund !== '')) {
      completed.push('needs');
    }
    
    // Existing Coverage
    if (formData.existingCoverage && formData.existingCoverage !== '') {
      completed.push('coverage');
    }
    
    // Policy Preferences
    if (formData.coveragePeriod && formData.coveragePeriod !== '' && 
        formData.inflationProtection && formData.inflationProtection !== '' && 
        formData.inflationProtection !== 'no') {
      completed.push('policy');
    }
    
    return completed;
  }, [formData]);

  const updateTickets = useCallback((completedSections: string[]) => {
    const updatedTickets = tickets.map(ticket => {
      const isNowCompleted = completedSections.includes(ticket.sectionId);
      const wasCompleted = ticket.isCompleted;
      
      return {
        ...ticket,
        isCompleted: isNowCompleted,
        completedAt: isNowCompleted && !wasCompleted ? new Date() : ticket.completedAt
      };
    });
    
    setTickets(updatedTickets);
    
    // Trigger section complete callbacks
    completedSections.forEach(sectionId => {
      onSectionComplete(sectionId);
    });
  }, [tickets, onSectionComplete]);

  useEffect(() => {
    const completedSections = checkCompletedSections();
    updateTickets(completedSections);
  }, [formData, checkCompletedSections, updateTickets]);

  const completedCount = tickets.filter(ticket => ticket.isCompleted).length;
  const totalCount = tickets.length;

  return (
    <div className="gamification-system simple">
      {/* Tickets Toggle */}
      <button
        className="tickets-toggle"
        onClick={() => setShowTickets(!showTickets)}
        title="View completion tickets"
      >
        🎫 Progress ({completedCount}/{totalCount})
      </button>

      {/* Tickets Panel */}
      {showTickets && (
        <div className="tickets-panel">
          <div className="tickets-header">
            <h3>Completion Tickets</h3>
            <button
              className="close-btn"
              onClick={() => setShowTickets(false)}
            >
              ✕
            </button>
          </div>
          
          <div className="tickets-grid">
            {tickets.map((ticket) => (
              <div
                key={ticket.id}
                className={`ticket ${ticket.isCompleted ? 'completed' : 'pending'}`}
              >
                <div className="ticket-icon">
                  {ticket.isCompleted ? '✅' : ticket.icon}
                </div>
                <div className="ticket-content">
                  <h4 className="ticket-title">{ticket.title}</h4>
                  <p className="ticket-description">{ticket.description}</p>
                  {ticket.isCompleted && ticket.completedAt && (
                    <span className="completion-time">
                      Completed at {ticket.completedAt.toLocaleTimeString()}
                    </span>
                  )}
                </div>
                {ticket.isCompleted && (
                  <div className="ticket-stamp">
                    <span className="stamp-text">DONE</span>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default GameificationSystem; 