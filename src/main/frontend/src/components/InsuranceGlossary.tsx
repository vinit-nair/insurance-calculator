import React, { useState } from 'react';

interface GlossaryTerm {
  term: string;
  definition: string;
  category: string;
}

const InsuranceGlossary: React.FC = () => {
  const [activeCategory, setActiveCategory] = useState<string>('basic');

  const glossaryTerms: GlossaryTerm[] = [
    // Basic Terms
    {
      term: "Life Insurance",
      definition: "A contract between you and an insurance company where you pay premiums, and in return, the insurer pays a lump sum (death benefit) to your beneficiaries when you die. This money helps replace your income and covers expenses.",
      category: "basic"
    },
    {
      term: "Premium",
      definition: "The amount you pay to the insurance company, usually monthly or annually, to keep your life insurance policy active. Think of it like a subscription fee for your coverage.",
      category: "basic"
    },
    {
      term: "Death Benefit",
      definition: "The amount of money your beneficiaries (family/dependents) will receive when you die. This is the 'coverage amount' we calculate in this tool. Also called the 'sum assured' in the UK.",
      category: "basic"
    },
    {
      term: "Beneficiaries",
      definition: "The people you choose to receive the death benefit when you die. Usually your spouse, children, or other family members who depend on your income.",
      category: "basic"
    },
    {
      term: "Dependents",
      definition: "People who rely on your income for their living expenses - typically your spouse, children, elderly parents, or anyone else you financially support.",
      category: "basic"
    },

    // Policy Types
    {
      term: "Term Life Insurance",
      definition: "Temporary life insurance that covers you for a specific period (10, 20, 30 years). It's cheaper but expires at the end of the term. Best for temporary needs like mortgages or until children are independent.",
      category: "policies"
    },
    {
      term: "Whole Life Insurance",
      definition: "Permanent life insurance that covers you for your entire life and includes a savings component (cash value). More expensive but builds wealth over time. Good for estate planning.",
      category: "policies"
    },
    {
      term: "Level Term",
      definition: "A term policy where the death benefit stays the same throughout the policy period. Most common type of term insurance.",
      category: "policies"
    },
    {
      term: "Decreasing Term",
      definition: "A term policy where the death benefit reduces over time, often used for mortgage protection as your mortgage balance decreases.",
      category: "policies"
    },
    {
      term: "Convertible Term",
      definition: "A term policy that allows you to convert to permanent life insurance later without a medical exam. Provides flexibility as your needs change.",
      category: "policies"
    },

    // Financial Terms
    {
      term: "Underwriting",
      definition: "The process where the insurance company evaluates your health, lifestyle, and finances to determine your premium and whether to offer you coverage. Usually involves a medical exam and questionnaire.",
      category: "financial"
    },
    {
      term: "Sum Assured",
      definition: "UK term for the death benefit - the guaranteed amount your beneficiaries will receive. This is what we calculate as your recommended coverage in this tool.",
      category: "financial"
    },
    {
      term: "Inflation Protection (RPI-Linked)",
      definition: "A feature where your coverage amount increases each year with the Retail Price Index (UK inflation measure) to maintain purchasing power. Costs about 20% more in premiums.",
      category: "financial"
    },
    {
      term: "Gross Income",
      definition: "Your total income before taxes and deductions. This is what employers pay you before HMRC takes income tax and National Insurance contributions.",
      category: "financial"
    },
    {
      term: "Net Income",
      definition: "Your take-home pay after taxes, National Insurance, and other deductions. What actually goes into your bank account each month.",
      category: "financial"
    },

    // Calculation Methods
    {
      term: "Income Replacement Method",
      definition: "Calculates coverage as 10-12 times your annual income. The most common and widely accepted method in the UK. Assumes your family needs your income for 10-12 years to adjust.",
      category: "calculations"
    },
    {
      term: "DIME Method",
      definition: "Calculates coverage based on four categories: Debt (what you owe), Income (replacement needed), Mortgage (outstanding balance), and Education (children's costs). Provides systematic coverage calculation.",
      category: "calculations"
    },
    {
      term: "Needs Analysis",
      definition: "The most comprehensive method that calculates exactly what your family would need to maintain their lifestyle, pay debts, and achieve goals. Considers all expenses over time.",
      category: "calculations"
    },
    {
      term: "Human Life Value",
      definition: "Calculates the present value of your future earnings until retirement. Uses financial formulas to determine your economic worth to your family.",
      category: "calculations"
    },
    {
      term: "Present Value",
      definition: "The current worth of future money. £1,000 in 10 years is worth less than £1,000 today due to inflation and investment returns. We use 3% discount rate for UK calculations.",
      category: "calculations"
    },

    // UK Specific
    {
      term: "Bereavement Support Payment",
      definition: "UK government benefit paid to surviving spouses/partners. Provides £3,500 lump sum plus monthly payments for 18 months (if no children) or until youngest child turns 18.",
      category: "uk"
    },
    {
      term: "Inheritance Tax (IHT)",
      definition: "UK tax on estates over £325,000 (nil-rate band). Life insurance can help pay this 40% tax so your family keeps more of your assets. Policies written in trust avoid IHT.",
      category: "uk"
    },
    {
      term: "Trust",
      definition: "Legal arrangement where your life insurance is held outside your estate, avoiding inheritance tax and providing faster payout to beneficiaries. Essential for larger policies.",
      category: "uk"
    },
    {
      term: "Group Life Insurance",
      definition: "Life insurance provided by your employer, typically 2-4 times your salary. Often called 'death in service' benefit. Good starting point but usually insufficient for full family needs.",
      category: "uk"
    },
    {
      term: "Critical Illness Cover",
      definition: "Pays out if you're diagnosed with serious illnesses like cancer, heart attack, or stroke. Can be added to life insurance or bought separately. Helps with medical costs and income replacement.",
      category: "uk"
    }
  ];

  const categories = [
    { key: 'basic', label: 'Basic Terms', icon: '📚' },
    { key: 'policies', label: 'Policy Types', icon: '📋' },
    { key: 'financial', label: 'Financial Terms', icon: '💰' },
    { key: 'calculations', label: 'Calculation Methods', icon: '🔢' },
    { key: 'uk', label: 'UK Specific', icon: '🇬🇧' }
  ];

  const filteredTerms = glossaryTerms.filter(term => term.category === activeCategory);

  return (
    <div className="insurance-glossary">
      <div className="glossary-header">
        <h3>📖 Life Insurance Glossary</h3>
        <p>Understanding insurance terminology helps you make better decisions. Click on categories below to learn key terms.</p>
      </div>

      <div className="glossary-categories">
        {categories.map(category => (
          <button
            key={category.key}
            className={`category-btn ${activeCategory === category.key ? 'active' : ''}`}
            onClick={() => setActiveCategory(category.key)}
          >
            <span className="category-icon">{category.icon}</span>
            <span className="category-label">{category.label}</span>
          </button>
        ))}
      </div>

      <div className="glossary-content">
        <div className="terms-grid">
          {filteredTerms.map((term, index) => (
            <div key={index} className="term-card">
              <div className="term-header">
                <h4>{term.term}</h4>
              </div>
              <div className="term-definition">
                <p>{term.definition}</p>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="glossary-footer">
        <div className="help-note">
          <strong>💡 Pro Tip:</strong> Hover over the ℹ️ icons throughout the calculator for context-specific explanations of these terms.
        </div>
      </div>
    </div>
  );
};

export default InsuranceGlossary; 