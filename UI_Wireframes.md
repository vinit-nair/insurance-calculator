# 📱 UI Wireframes & User Flow
## Lloyds Banking Group - UK Insurance Calculator

---

## 🎯 User Journey Overview

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              USER JOURNEY MAP                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  AWARENESS → INTEREST → CONSIDERATION → CALCULATION → DECISION → ACTION     │
│                                                                             │
│  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐      │
│  │Customer │   │Visits   │   │Reads    │   │Completes│   │Reviews  │      │
│  │realizes │   │Lloyds   │   │intro &  │   │gets     │   │decides  │      │
│  │need for │   │insurance│   │features │   │results  │   │next step│      │
│  │life     │   │calculator│   │         │   │results  │   │         │      │
│  │insurance│   │         │   │         │   │         │   │         │      │
│  └─────────┘   └─────────┘   └─────────┘   └─────────┘   └─────────┘      │
│                                                                             │
│     2min           30sec         2min         5-8min        5-10min        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📱 Mobile-First Wireframes

### 1. Landing Page (Mobile)

```
┌─────────────────────────────────┐
│ ≡  Lloyds Banking Group    🔍   │ ← Header with menu & search
├─────────────────────────────────┤
│                                 │
│    🛡️ Life Insurance            │ ← Hero section
│    Needs Calculator             │
│                                 │
│    Find Your Perfect Coverage   │
│    in Just 10 Minutes          │
│                                 │
│  ┌─────────────────────────────┐ │
│  │     GET STARTED NOW         │ │ ← Primary CTA button
│  └─────────────────────────────┘ │
│                                 │
│ ┌─────┐ ┌─────┐ ┌─────┐         │ ← Feature highlights
│ │ 🎯  │ │ 📊  │ │ 💡  │         │
│ │Pers-│ │Multi│ │Expert│        │
│ │onal │ │Meth-│ │Guide │        │
│ │ized │ │ods  │ │     │        │
│ └─────┘ └─────┘ └─────┘         │
│                                 │
│ Uses 4 proven calculation       │ ← Key benefits
│ methods with UK market data     │
│                                 │
│ ┌─────────────────────────────┐ │
│ │ 📖 Insurance Terms Glossary │ │ ← Help section
│ └─────────────────────────────┘ │
│                                 │
│ New to life insurance? Click    │
│ above to understand key terms   │
│                                 │
└─────────────────────────────────┘
```

### 2. Form Section - Personal Details (Mobile)

```
┌─────────────────────────────────┐
│ ←  About You                    │ ← Section header with back
├─────────────────────────────────┤
│                                 │
│ Let's start with basic info     │ ← Section description
│ about yourself                  │
│                                 │
│ Your Age *                      │ ← Required field label
│ ┌─────────────────────────────┐ │
│ │ Enter age (18-80)           │ │ ← Input with placeholder
│ └─────────────────────────────┘ │
│ ❓ Used to calculate coverage   │ ← Tooltip info
│   needs and premium rates      │
│                                 │
│ Gender *                        │
│ ┌─────────────────────────────┐ │
│ │ Select your gender      ▼   │ │ ← Dropdown select
│ └─────────────────────────────┘ │
│ ❓ Affects life expectancy and  │
│   insurance pricing             │
│                                 │
│ Smoking Status *                │
│ ┌─────────────────────────────┐ │
│ │ Select smoking status   ▼   │ │
│ └─────────────────────────────┘ │
│ ❓ Major factor in premium      │
│   calculation (2x impact)      │
│                                 │
│ ┌─────────────────────────────┐ │
│ │         CONTINUE            │ │ ← Continue button
│ └─────────────────────────────┘ │
│                                 │
│ Step 1 of 6                     │ ← Progress indicator
│ ████░░░░░░░░░░░░░░░░░░░░░░░░░░   │
│                                 │
└─────────────────────────────────┘
```

### 3. Form Section - Financial Information (Mobile)

```
┌─────────────────────────────────┐
│ ←  Your Financial Situation     │
├─────────────────────────────────┤
│                                 │
│ Help us understand your income  │
│ and expenses                    │
│                                 │
│ Annual Income (Gross) *         │
│ ┌─────────────────────────────┐ │
│ │ £ 35,000 (UK median)        │ │ ← Input with prefix
│ └─────────────────────────────┘ │
│ ❓ Enter gross salary before    │
│   taxes and deductions         │
│                                 │
│ Monthly Family Expenses *       │
│ ┌─────────────────────────────┐ │
│ │ £ 2,800 (UK average)        │ │
│ └─────────────────────────────┘ │
│ ❓ Include mortgage, bills,     │
│   food, transport costs        │
│                                 │
│ Partner's Annual Income         │
│ ┌─────────────────────────────┐ │
│ │ £ 30,000 (or 0 if single)   │ │
│ └─────────────────────────────┘ │
│                                 │
│ Current Savings & Investments   │
│ ┌─────────────────────────────┐ │
│ │ £ 15,000 (UK median)        │ │
│ └─────────────────────────────┘ │
│                                 │
│ ┌─────────────────────────────┐ │
│ │         CONTINUE            │ │
│ └─────────────────────────────┘ │
│                                 │
│ Step 2 of 6                     │
│ ████████░░░░░░░░░░░░░░░░░░░░░░   │
│                                 │
└─────────────────────────────────┘
```

### 4. Progress Indicator Modal (Mobile)

```
┌─────────────────────────────────┐
│                                 │
│  🔄 Calculating Your Insurance  │ ← Modal overlay
│     Needs                       │
│                                 │
│  Using UK actuarial standards   │
│  and market data                │
│                                 │
│ ████████████████████░░░░░░░░░   │ ← Progress bar
│ 75% Complete                    │
│                                 │
│ ┌─────────────────────────────┐ │
│ │ Step 6 of 8: Life Value     │ │ ← Current step
│ │ Calculation                 │ │
│ │                             │ │
│ │ Computing human life value  │ │
│ │ using actuarial methods     │ │
│ │                             │ │
│ │          ⏳                  │ │ ← Loading spinner
│ └─────────────────────────────┘ │
│                                 │
│ ✓ Initializing                  │ ← Completed steps
│ ✓ Fetching Data                 │
│ ✓ Income Analysis               │
│ ✓ DIME Analysis                 │
│ ✓ Needs Assessment              │
│ ⏳ Life Value Calculation       │ ← Current step
│ ⏸ Final Analysis                │ ← Pending steps
│ ⏸ Complete                      │
│                                 │
└─────────────────────────────────┘
```

### 5. Results Page - Coverage Recommendation (Mobile)

```
┌─────────────────────────────────┐
│ ←  Your Results                 │
├─────────────────────────────────┤
│                                 │
│ 🎯 RECOMMENDED COVERAGE         │
│                                 │
│    £485,000                     │ ← Large coverage amount
│                                 │
│ Based on UK actuarial best      │
│ practices and your specific     │
│ situation                       │
│                                 │
│ ┌─────────────────────────────┐ │
│ │ 💰 ESTIMATED PREMIUM        │ │
│ │                             │ │
│ │ £42 per month               │ │ ← Monthly premium
│ │ £504 per year               │ │
│ │                             │ │
│ │ Based on UK market rates    │ │
│ └─────────────────────────────┘ │
│                                 │
│ ┌─────────────────────────────┐ │
│ │    HOW WE CALCULATED        │ │ ← Expandable section
│ │         THIS               ▼│
│ └─────────────────────────────┘ │
│                                 │
│ • Income Replacement: £420,000  │ ← Method breakdown
│ • DIME Method: £510,000         │
│ • Needs Analysis: £475,000      │
│ • Human Life Value: £535,000    │
│                                 │
│ Final: Median = £485,000        │
│                                 │
│ ┌─────────────────────────────┐ │
│ │   GET PERSONALIZED QUOTE    │ │ ← Primary CTA
│ └─────────────────────────────┘ │
│                                 │
└─────────────────────────────────┘
```

---

## 💻 Desktop Wireframes

### 1. Landing Page (Desktop)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ Lloyds Banking Group          Home | Insurance | Banking | Contact    🔍 Search         │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  ┌─────────────────────────────────────┐  ┌─────────────────────────────────────────┐  │
│  │                                     │  │                                         │  │
│  │    🛡️ Life Insurance Calculator      │  │   ┌─────────────────────────────────┐   │  │
│  │                                     │  │   │                                 │   │  │
│  │    Find Your Perfect Coverage       │  │   │         QUICK START             │   │  │
│  │    in Just 10 Minutes              │  │   │                                 │   │  │
│  │                                     │  │   │  Already know what you need?    │   │  │
│  │    Our intelligent calculator       │  │   │  Jump straight to the form      │   │  │
│  │    analyzes your unique situation   │  │   │                                 │   │  │
│  │    using 4 proven methods          │  │   │  ┌─────────────────────────────┐ │   │  │
│  │                                     │  │   │  │     START CALCULATOR        │ │   │  │
│  │  ┌─────────────────────────────────┐ │  │   │  └─────────────────────────────┘ │   │  │
│  │  │        GET STARTED NOW          │ │  │   │                                 │   │  │
│  │  └─────────────────────────────────┘ │  │   └─────────────────────────────────┘   │  │
│  │                                     │  │                                         │  │
│  └─────────────────────────────────────┘  └─────────────────────────────────────────┘  │
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                              WHY CHOOSE OUR CALCULATOR                          │   │
│  ├─────────────────────────────────────────────────────────────────────────────────┤   │
│  │                                                                                 │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐   │   │
│  │  │     🎯      │  │     📊      │  │     💡      │  │        🏛️           │   │   │
│  │  │             │  │             │  │             │  │                     │   │   │
│  │  │ Personalized│  │   Multiple  │  │   Expert    │  │    UK-Specific      │   │   │
│  │  │  Analysis   │  │   Methods   │  │  Guidance   │  │    Data Sources     │   │   │
│  │  │             │  │             │  │             │  │                     │   │   │
│  │  │Tailored to  │  │Uses 4 proven│  │Professional │  │Official UK market   │   │   │
│  │  │your specific│  │calculation  │  │insights and │  │data from ABI, CMI,  │   │   │
│  │  │situation    │  │methods      │  │product recs │  │ONS, NHS, and RPI    │   │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                                HELP & SUPPORT                                  │   │
│  ├─────────────────────────────────────────────────────────────────────────────────┤   │
│  │                                                                                 │   │
│  │  📖 Insurance Terms Glossary     💬 Live Chat Support     📞 Call 0345 300 0000 │   │
│  │                                                                                 │   │
│  │  New to life insurance? Our interactive glossary explains 25+ key terms        │   │
│  │  in simple language. No confusing jargon or sales pressure.                   │   │
│  │                                                                                 │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### 2. Calculator Form (Desktop - Two Column Layout)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ ←  Life Insurance Calculator                                    Step 2 of 6 | Save & Exit │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  ┌─────────────────────────────────────┐  ┌─────────────────────────────────────────┐  │
│  │           FORM SECTION              │  │           HELP & GUIDANCE               │  │
│  ├─────────────────────────────────────┤  ├─────────────────────────────────────────┤  │
│  │                                     │  │                                         │  │
│  │  💰 Your Financial Situation        │  │  💡 Why We Need This Information        │  │
│  │                                     │  │                                         │  │
│  │  Help us understand your income     │  │  We use your financial information to   │  │
│  │  and expenses to calculate the      │  │  calculate the right coverage amount    │  │
│  │  right coverage amount.             │  │  using four proven methods:            │  │
│  │                                     │  │                                         │  │
│  │  Annual Income (Gross) *            │  │  • Income Replacement Method           │  │
│  │  ┌─────────────────────────────────┐ │  │  • DIME Method (Debt+Income+         │  │
│  │  │ £ Enter gross salary            │ │  │    Mortgage+Education)               │  │
│  │  └─────────────────────────────────┘ │  │  • Comprehensive Needs Analysis      │  │
│  │  ❓ Used to calculate 10-12x income  │  │  • Human Life Value Method           │  │
│  │    replacement needs                │  │                                         │  │
│  │                                     │  │  All calculations use official UK      │  │
│  │  Monthly Family Expenses *          │  │  data sources and regulatory           │  │
│  │  ┌─────────────────────────────────┐ │  │  standards.                         │  │
│  │  │ £ Include all household costs   │ │  │                                         │  │
│  │  └─────────────────────────────────┘ │  │  ┌─────────────────────────────────┐   │  │
│  │  ❓ Mortgage, bills, food, transport │  │  │    📖 VIEW GLOSSARY             │   │  │
│  │                                     │  │  └─────────────────────────────────┘   │  │
│  │  Partner's Annual Income            │  │                                         │  │
│  │  ┌─────────────────────────────────┐ │  │  ┌─────────────────────────────────┐   │  │
│  │  │ £ 0 if single/not working       │ │  │  │    💬 NEED HELP?                │   │  │
│  │  └─────────────────────────────────┘ │  │  │                                 │   │  │
│  │                                     │  │  │  Chat with our insurance        │   │  │
│  │  Current Savings & Investments      │  │  │  specialists for personalized   │   │  │
│  │  ┌─────────────────────────────────┐ │  │  │  guidance                       │   │  │
│  │  │ £ ISAs, savings, investments    │ │  │  │                                 │   │  │
│  │  └─────────────────────────────────┘ │  │  │  ┌─────────────────────────────┐ │   │  │
│  │                                     │  │  │  │      START CHAT             │ │   │  │
│  │  ┌─────────────────────────────────┐ │  │  │  └─────────────────────────────┘ │   │  │
│  │  │           CONTINUE              │ │  │  └─────────────────────────────────┘   │  │
│  │  └─────────────────────────────────┘ │  │                                         │  │
│  │                                     │  │                                         │  │
│  └─────────────────────────────────────┘  └─────────────────────────────────────────┘  │
│                                                                                         │
│  Progress: ████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ 33% Complete                      │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### 3. Results Dashboard (Desktop)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ Your Life Insurance Recommendation                               📧 Email Results | 🖨️ Print │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                            🎯 RECOMMENDED COVERAGE                              │   │
│  ├─────────────────────────────────────────────────────────────────────────────────┤   │
│  │                                                                                 │   │
│  │                                £485,000                                         │   │
│  │                                                                                 │   │
│  │         Based on UK actuarial best practices and your specific situation       │   │
│  │                                                                                 │   │
│  │  ┌─────────────────────────────────────────────────────────────────────────┐   │   │
│  │  │                        💰 ESTIMATED PREMIUM                            │   │   │
│  │  │                                                                         │   │   │
│  │  │  Monthly: £42      Annual: £504      Total over 20 years: £10,080      │   │   │
│  │  │                                                                         │   │   │
│  │  │  ✓ Based on current UK market rates  ✓ Includes inflation protection   │   │   │
│  │  └─────────────────────────────────────────────────────────────────────────┘   │   │
│  │                                                                                 │   │
│  │  ┌─────────────────────────────────────────────────────────────────────────┐   │   │
│  │  │                      GET PERSONALIZED QUOTE                            │   │   │
│  │  └─────────────────────────────────────────────────────────────────────────┘   │   │
│  │                                                                                 │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  ┌─────────────────────────────────────┐  ┌─────────────────────────────────────────┐  │
│  │        CALCULATION BREAKDOWN        │  │        PERSONALIZED RECOMMENDATIONS    │  │
│  ├─────────────────────────────────────┤  ├─────────────────────────────────────────┤  │
│  │                                     │  │                                         │  │
│  │  How we calculated £485,000:        │  │  🎯 Recommended Primary Product         │  │
│  │                                     │  │                                         │  │
│  │  ┌─ Income Replacement: £420,000    │  │  20-Year Level Term Life Insurance      │  │
│  │  ┌─ DIME Method: £510,000           │  │  £485,000 Coverage                     │  │
│  │  ┌─ Needs Analysis: £475,000        │  │                                         │  │
│  │  └─ Human Life Value: £535,000      │  │  KEY FEATURES:                         │  │
│  │                                     │  │  • Guaranteed Level Premiums          │  │
│  │  UK-Compliant Median: £485,000     │  │  • Convertible Option                  │  │
│  │                                     │  │  • Accidental Death Benefit           │  │
│  │  Why median? More stable than       │  │  • Terminal Illness Benefit           │  │
│  │  weighted averages, follows UK      │  │                                         │  │
│  │  actuarial standards.               │  │  👨‍👩‍👧‍👦 Family Protection Package      │  │
│  │                                     │  │                                         │  │
│  │  ┌─────────────────────────────────┐ │  │  Consider adding:                      │  │
│  │  │     VIEW DETAILED EXPLANATION   │ │  │  • Family Income Benefit               │  │
│  │  └─────────────────────────────────┘ │  │  • Child Benefit Rider                │  │
│  │                                     │  │  • Waiver of Premium                   │  │
│  │                                     │  │  • Joint Life Policy                   │  │
│  │                                     │  │                                         │  │
│  │                                     │  │  ┌─────────────────────────────────┐   │  │
│  │                                     │  │  │      EXPLORE PRODUCTS           │   │  │
│  │                                     │  │  └─────────────────────────────────┘   │  │
│  │                                     │  │                                         │  │
│  └─────────────────────────────────────┘  └─────────────────────────────────────────┘  │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 User Flow Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                                  USER FLOW DIAGRAM                                     │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  START                                                                                  │
│    │                                                                                   │
│    ▼                                                                                   │
│  ┌─────────────────┐                                                                  │
│  │   Landing Page  │ ────────────────┐                                               │
│  │                 │                 │                                               │
│  │ • Hero section  │                 │                                               │
│  │ • Features      │                 │                                               │
│  │ • Help section  │                 │                                               │
│  └─────────────────┘                 │                                               │
│           │                          │                                               │
│           ▼                          ▼                                               │
│  ┌─────────────────┐        ┌─────────────────┐                                     │
│  │ Glossary Modal  │        │  Quick Start    │                                     │
│  │                 │        │                 │                                     │
│  │ • 25+ terms     │        │ • Skip intro    │                                     │
│  │ • Simple explain│        │ • Direct to form│                                     │
│  │ • Interactive   │        │                 │                                     │
│  └─────────────────┘        └─────────────────┘                                     │
│           │                          │                                               │
│           └──────────┬───────────────┘                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Form Section 1  │                                                     │
│              │ Personal Details│                                                     │
│              │                 │                                                     │
│              │ • Age           │                                                     │
│              │ • Gender        │                                                     │
│              │ • Smoking       │                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Form Section 2  │                                                     │
│              │ Financial Info  │                                                     │
│              │                 │                                                     │
│              │ • Income        │                                                     │
│              │ • Expenses      │                                                     │
│              │ • Savings       │                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Form Section 3  │                                                     │
│              │ Debts & Costs   │                                                     │
│              │                 │                                                     │
│              │ • Mortgage      │                                                     │
│              │ • Other debts   │                                                     │
│              │ • Funeral costs │                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Form Section 4  │                                                     │
│              │ Family & Goals  │                                                     │
│              │                 │                                                     │
│              │ • Dependents    │                                                     │
│              │ • Education     │                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Form Section 5  │                                                     │
│              │ Existing Cover  │                                                     │
│              │                 │                                                     │
│              │ • Current life  │                                                     │
│              │   insurance     │                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Form Section 6  │                                                     │
│              │ Preferences     │                                                     │
│              │                 │                                                     │
│              │ • Coverage term │                                                     │
│              │ • Inflation     │                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Progress Modal  │                                                     │
│              │                 │                                                     │
│              │ • 8-step process│                                                     │
│              │ • Progress bar  │                                                     │
│              │ • Status updates│                                                     │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│              ┌─────────────────┐                                                     │
│              │ Results Page    │                                                     │
│              │                 │                                                     │
│              │ • Coverage rec  │                                                     │
│              │ • Premium est   │                                                     │
│              │ • Explanations  │                                                     │
│              │ • Recommendations│                                                    │
│              └─────────────────┘                                                     │
│                      │                                                               │
│                      ▼                                                               │
│  ┌─────────────────┐ │ ┌─────────────────┐ │ ┌─────────────────┐                   │
│  │   Get Quote     │ │ │  Email Results  │ │ │   Print/Save    │                   │
│  │                 │ │ │                 │ │ │                 │                   │
│  │ • Contact form  │ │ │ • PDF report    │ │ │ • PDF download  │                   │
│  │ • Schedule call │ │ │ • Follow-up     │ │ │ • Print option  │                   │
│  └─────────────────┘ │ └─────────────────┘ │ └─────────────────┘                   │
│                      │                     │                                       │
│                      ▼                     ▼                                       │
│                   END                   END                                        │
│                                                                                     │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Interactive Elements & States

### Form Field States

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              FORM FIELD INTERACTION STATES                             │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  DEFAULT STATE                   FOCUS STATE                    ERROR STATE             │
│  ┌─────────────────────────────┐  ┌─────────────────────────────┐  ┌─────────────────────┐ │
│  │ Enter your age              │  │ Enter your age              │  │ 150                 │ │
│  └─────────────────────────────┘  └─────────────────────────────┘  └─────────────────────┘ │
│   Border: #ddd                    Border: #006b3d (green)         Border: #d32f2f (red)  │
│                                   Shadow: 0 0 0 2px rgba...       Background: #ffeaea    │
│                                                                                           │
│                                                                    ❌ Age must be 18-80   │
│                                                                                           │
│  VALID STATE                     TOOLTIP ACTIVE                  DISABLED STATE          │
│  ┌─────────────────────────────┐  ┌─────────────────────────────┐  ┌─────────────────────┐ │
│  │ 35                      ✓   │  │ 35                      ❓   │  │ Calculating...      │ │
│  └─────────────────────────────┘  └─────────────────────────────┘  └─────────────────────┘ │
│   Border: #4caf50 (green)         ┌─────────────────────────────┐   Background: #f5f5f5   │
│   Icon: ✓ checkmark               │ Used to calculate coverage  │   Color: #999           │
│                                   │ needs and premium rates     │   Cursor: not-allowed   │
│                                   └─────────────────────────────┘                         │
│                                                                                           │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### Button States

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                                 BUTTON INTERACTION STATES                              │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  PRIMARY BUTTON                  HOVER STATE                    ACTIVE/PRESSED          │
│  ┌─────────────────────────────┐  ┌─────────────────────────────┐  ┌─────────────────────┐ │
│  │        CONTINUE             │  │        CONTINUE             │  │      CONTINUE       │ │
│  └─────────────────────────────┘  └─────────────────────────────┘  └─────────────────────┘ │
│   Background: #006b3d             Background: #005530             Background: #004225    │
│   Color: white                    Transform: translateY(-1px)      Transform: scale(0.98) │
│   Border-radius: 8px              Box-shadow: 0 4px 12px...       Box-shadow: inset...   │
│                                                                                           │
│  DISABLED STATE                  LOADING STATE                   SECONDARY BUTTON        │
│  ┌─────────────────────────────┐  ┌─────────────────────────────┐  ┌─────────────────────┐ │
│  │        CONTINUE             │  │    ⏳ CALCULATING...         │  │    VIEW GLOSSARY    │ │
│  └─────────────────────────────┘  └─────────────────────────────┘  └─────────────────────┘ │
│   Background: #cccccc             Background: #006b3d             Background: transparent │
│   Color: #666666                  Color: white                    Color: #006b3d         │
│   Cursor: not-allowed             Cursor: wait                    Border: 2px solid...   │
│                                   Animation: spinner              Hover: background #f5f5│
│                                                                                           │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### Progress Indicator States

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              PROGRESS INDICATOR VARIATIONS                             │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  FORM PROGRESS (Bottom of page)                                                        │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │ Step 3 of 6                                                                     │   │
│  │ ████████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░   │   │
│  │ 50% Complete                                                                    │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  CALCULATION PROGRESS (Modal overlay)                                                  │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                          🔄 Calculating Your Insurance Needs                    │   │
│  │                                                                                 │   │
│  │  ████████████████████████████████████████████████████████████░░░░░░░░░░░░░░░   │   │
│  │  87% Complete                                                                  │   │
│  │                                                                                 │   │
│  │  ┌─────────────────────────────────────────────────────────────────────────┐   │   │
│  │  │ Step 7 of 8: Final Analysis                                            │   │   │
│  │  │ Calculating median recommendation and premium estimates                 │   │   │
│  │  │                                    ⏳                                   │   │   │
│  │  └─────────────────────────────────────────────────────────────────────────┘   │   │
│  │                                                                                 │   │
│  │  ✓ Initializing                                                                │   │
│  │  ✓ Fetching Data                                                               │   │
│  │  ✓ Income Analysis                                                             │   │
│  │  ✓ DIME Analysis                                                               │   │
│  │  ✓ Needs Assessment                                                            │   │
│  │  ✓ Life Value Calculation                                                      │   │
│  │  ⏳ Final Analysis                                                              │   │
│  │  ⏸ Complete                                                                    │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

This comprehensive wireframe documentation provides:

1. **Complete User Journey** - From awareness to action
2. **Mobile-First Design** - Responsive layouts for all screen sizes
3. **Desktop Layouts** - Optimized for larger screens with two-column layouts
4. **Interactive States** - All form field and button states
5. **Progress Indicators** - Both form progress and calculation progress
6. **User Flow Diagram** - Complete navigation paths
7. **Accessibility Considerations** - Screen reader support and keyboard navigation

These wireframes demonstrate the professional UI/UX design approach and will significantly enhance your hackathon presentation by showing the complete user experience design. 