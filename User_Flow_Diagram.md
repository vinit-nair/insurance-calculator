# 🔄 User Flow Diagram
## UK Insurance Calculator - Complete User Journey

```
                                    START
                                      │
                                      ▼
                            ┌─────────────────┐
                            │   Landing Page  │
                            │                 │
                            │ • Hero Section  │
                            │ • Features      │
                            │ • Get Started   │
                            │ • Help/Glossary │
                            └─────────────────┘
                                      │
                        ┌─────────────┼─────────────┐
                        ▼             ▼             ▼
              ┌─────────────────┐   Continue   ┌─────────────────┐
              │ Insurance       │              │ Quick Start     │
              │ Glossary Modal  │              │ (Skip Intro)    │
              │                 │              │                 │
              │ • 25+ Terms     │              │ • Direct to     │
              │ • Definitions   │              │   Form          │
              │ • Close/Return  │              │                 │
              └─────────────────┘              └─────────────────┘
                        │                               │
                        └─────────────┬─────────────────┘
                                      ▼
                            ┌─────────────────┐
                            │ Form Section 1  │
                            │ Personal Info   │
                            │                 │
                            │ • Age (18-80)   │
                            │ • Gender        │
                            │ • Smoking Status│
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Form Section 2  │
                            │ Financial Info  │
                            │                 │
                            │ • Annual Income │
                            │ • Monthly Exp.  │
                            │ • Partner Income│
                            │ • Savings       │
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Form Section 3  │
                            │ Debts & Costs   │
                            │                 │
                            │ • Mortgage      │
                            │ • Other Debts   │
                            │ • Funeral Costs │
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Form Section 4  │
                            │ Family & Goals  │
                            │                 │
                            │ • Dependents    │
                            │ • Children      │
                            │ • Education     │
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Form Section 5  │
                            │ Existing Cover  │
                            │                 │
                            │ • Current Life  │
                            │   Insurance     │
                            │ • Coverage Gap  │
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Form Section 6  │
                            │ Preferences     │
                            │                 │
                            │ • Coverage Term │
                            │ • Inflation     │
                            │   Protection    │
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Progress Modal  │
                            │ (Overlay)       │
                            │                 │
                            │ • 8-Step Process│
                            │ • Progress Bar  │
                            │ • Status Updates│
                            │ • Loading Anim. │
                            └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Results Page    │
                            │                 │
                            │ • Coverage Rec  │
                            │ • Premium Est   │
                            │ • Explanations  │
                            │ • Breakdown     │
                            │ • Recommendations│
                            └─────────────────┘
                                      │
                ┌─────────────────────┼─────────────────────┐
                ▼                     ▼                     ▼
      ┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐
      │   Get Quote     │   │  Email Results  │   │   Save/Print    │
      │                 │   │                 │   │                 │
      │ • Contact Form  │   │ • PDF Report    │   │ • PDF Download  │
      │ • Schedule Call │   │ • Follow-up     │   │ • Print Option  │
      │ • Product Info  │   │ • Reminders     │   │ • Share Results │
      └─────────────────┘   └─────────────────┘   └─────────────────┘
                │                     │                     │
                ▼                     ▼                     ▼
              END                   END                   END
```

## 📱 Decision Points & Navigation Paths

```
LANDING PAGE DECISIONS:
├── New User → View Glossary → Return to Form
├── Returning User → Quick Start → Skip to Form
└── Interested User → Read Features → Start Form

FORM NAVIGATION:
├── Forward Progress → Next Section (Validation Required)
├── Backward Progress → Previous Section (Data Preserved)
├── Exit → Save Progress (Optional) → Return Later
└── Help → Tooltip/Modal → Continue Form

RESULTS PAGE ACTIONS:
├── Satisfied → Get Quote → Contact/Schedule
├── Want Details → Email Results → Follow-up
├── Need Copy → Save/Print → Personal Records
└── Unsatisfied → Back to Form → Adjust Inputs
```

## ⏱️ User Journey Timing

```
PHASE                    ESTIMATED TIME    COMPLETION RATE
─────────────────────────────────────────────────────────
Landing Page             30-60 seconds     95%
Form Completion          5-8 minutes       78%
Progress/Calculation     15-30 seconds     98%
Results Review           3-5 minutes       85%
Action Decision          2-10 minutes      65%
─────────────────────────────────────────────────────────
TOTAL JOURNEY           11-24 minutes      52%
```

## 🔄 User Flow States

```
ENTRY POINTS:
• Direct URL Access
• Lloyds Website Navigation
• Search Engine Results
• Marketing Campaign Links

EXIT POINTS:
• Quote Request Submitted
• Results Emailed/Saved
• Form Abandoned (Partial)
• Browser Closed

RETURN PATHS:
• Email Link (Results)
• Bookmarked URL
• Browser History
• Follow-up Communications
``` 