# Calculation Configuration Documentation

## Overview

The insurance calculator has been refactored to use a highly configurable architecture that externalizes all business logic into configuration files. This allows for:

- **Zero code changes** for business rule modifications
- **Runtime configuration updates** without redeployment
- **Easy addition of new calculation types** (mortgage, pension, etc.)
- **A/B testing** of different calculation methods
- **Multi-tenant configuration** support

## Configuration File Structure

The main configuration file is located at: `src/main/resources/calculation-config.yml`

### Root Structure

```yaml
calculations:
  insurance:           # Calculation type identifier
    type: "life-insurance"
    description: "UK Life Insurance Calculator"
    version: "1.0"
    methods: []        # Available calculation methods
    validation: {}     # Input validation rules
    premium: {}        # Premium calculation configuration
    recommendation: {} # Recommendation weighting and limits
    dataSources: []    # Data source information
    performance: {}    # Performance and caching settings
```

## Calculation Methods Configuration

### Income Replacement Method

```yaml
- name: "income-replacement"
  displayName: "Income Replacement Method"
  description: "Calculates coverage as a multiple of annual income"
  formula: "annualIncome * multiplier"
  multiplier:
    base: 10                    # Base multiplier (years)
    adjustments:
      age:
        18-30: 1.2             # Age-based adjustments
        31-50: 1.0
        51-80: 0.8
      dependents:
        0: 0.8                 # Dependents-based adjustments
        1-2: 1.0
        3+: 1.3
      coveragePeriod:
        "10": 0.8              # Term-based adjustments
        "15": 0.9
        "20": 1.0
        "25": 1.1
        "30": 1.2
        "whole": 1.5
  explanation: "Income Replacement Method explanation..."
```

**Formula**: `annualIncome × (base × ageFactor × dependentsFactor × periodFactor)`

### DIME Method

```yaml
- name: "dime"
  displayName: "DIME Method"
  description: "Debt, Income, Mortgage, Education calculation"
  formula: "otherDebts + incomeNeeds + mortgage + educationFund"
  incomeYears:
    "10": 15                   # Years of income replacement by term
    "15": 18
    "20": 20
    "25": 22
    "30": 25
    "whole": 30
```

**Formula**: `otherDebts + (annualIncome - spouseIncome) × incomeYears + mortgage + educationFund`

### Needs Analysis Method

```yaml
- name: "needs-analysis"
  displayName: "Needs Analysis Method"
  description: "Comprehensive analysis of family financial needs"
  formula: "monthlyExpenses * 12 * yearsOfSupport + funeralCosts + otherDebts - savings"
  yearsOfSupport:
    base: 20
    adjustments:
      age:
        18-30: 25              # Age-based years of support
        31-40: 22
        41-50: 20
        51-60: 18
        61-80: 15
      dependents:
        0: 15                   # Dependents-based years of support
        1-2: 20
        3+: 25
```

**Formula**: `monthlyExpenses × 12 × yearsOfSupport + funeralCosts + otherDebts - savings`

### Human Life Value Method

```yaml
- name: "human-life-value"
  displayName: "Human Life Value Method"
  description: "Present value of future earnings"
  formula: "presentValue(annualIncome - spouseIncome, workingYears, discountRate)"
  workingYears:
    base: 65
    adjustments:
      age:
        18-30: 35              # Working years by age
        31-40: 30
        41-50: 25
        51-60: 20
        61-80: 15
  discountRate: 0.03           # Discount rate for present value calculation
```

**Formula**: `presentValue(annualIncome - spouseIncome, workingYears, 0.03)`

## Validation Rules Configuration

### Required Fields

```yaml
validation:
  required:
    - "age"
    - "gender"
    - "smoking"
    - "annualIncome"
    - "monthlyExpenses"
    - "coveragePeriod"
```

### Range Validation

```yaml
validation:
  ranges:
    age:
      min: 18
      max: 80
      message: "Age must be between 18 and 80"
    dependents:
      min: 0
      max: 10
      message: "Number of dependents must be between 0 and 10"
    annualIncome:
      min: 0
      message: "Annual income must be positive"
```

### Pattern Validation

```yaml
validation:
  patterns:
    gender:
      regex: "^(male|female)$"
      message: "Gender must be 'male' or 'female'"
    smoking:
      regex: "^(non-smoker|smoker|ex-smoker)$"
      message: "Invalid smoking status"
    coveragePeriod:
      regex: "^(10|15|20|25|30|whole)$"
      message: "Invalid coverage period"
```

### Default Values

```yaml
validation:
  defaults:
    existingCoverage: 0.0
    savings: 0.0
    mortgage: 0.0
    otherDebts: 0.0
    funeralCosts: 4000.0
    dependents: 0
    spouseIncome: 0.0
    educationFund: 0.0
    inflationProtection: "no"
```

## Premium Calculation Configuration

### Base Rate and Factors

```yaml
premium:
  baseRate: 0.001              # Base premium rate (0.1%)
  factors:
    age:
      source: "age_factor_table"
      description: "Age-based premium factors"
    smoking:
      source: "smoking_factor_table"
      description: "Smoking status premium factors"
    gender:
      source: "gender_factor_table"
      description: "Gender-based premium factors"
    coveragePeriod:
      source: "term_factor_table"
      description: "Coverage period premium factors"
    inflationProtection:
      source: "inflation_factor_table"
      description: "Inflation protection premium factors"
```

### Factor Tables

```yaml
premium:
  factorTables:
    age_factor_table:
      18-25: 0.8               # Age-based premium multipliers
      26-30: 0.9
      31-35: 1.0
      36-40: 1.2
      41-45: 1.5
      46-50: 2.0
      51-55: 2.8
      56-60: 3.5
      61-65: 4.5
      66-70: 6.0
      71-75: 8.0
      76-80: 10.0
    
    smoking_factor_table:
      "non-smoker": 1.0
      "ex-smoker": 1.2
      "smoker": 2.5
    
    gender_factor_table:
      "male": 1.2
      "female": 1.0
    
    term_factor_table:
      "10": 0.8
      "15": 0.9
      "20": 1.0
      "25": 1.1
      "30": 1.2
      "whole": 1.5
    
    inflation_factor_table:
      "no": 1.0
      "yes": 1.3
```

**Premium Formula**: `coverageAmount × baseRate × ageFactor × smokingFactor × genderFactor × termFactor × inflationFactor`

## Recommendation Configuration

### Method Weights

```yaml
recommendation:
  weights:
    income-replacement: 0.3     # 30% weight for income replacement method
    dime: 0.25                  # 25% weight for DIME method
    needs-analysis: 0.25        # 25% weight for needs analysis method
    human-life-value: 0.2       # 20% weight for human life value method
```

### Coverage Limits

```yaml
recommendation:
  limits:
    minimum: 50000              # Minimum recommended coverage
    maximum: 10000000           # Maximum recommended coverage
```

### Rounding Rules

```yaml
recommendation:
  rounding:
    method: "nearest"           # Rounding method (nearest, up, down)
    increment: 1000             # Round to nearest £1,000
```

## Data Sources Configuration

```yaml
dataSources:
  - name: "ABI Market Data"
    description: "Association of British Insurers market rates"
    url: "https://www.abi.org.uk"
  - name: "UK Mortality Tables"
    description: "Continuous Mortality Investigation data"
    url: "https://www.cmi.org.uk"
  - name: "FCA Guidelines"
    description: "Financial Conduct Authority regulations"
    url: "https://www.fca.org.uk"
```

## Performance Configuration

```yaml
performance:
  cacheEnabled: true            # Enable calculation caching
  cacheTTL: 3600               # Cache time-to-live (1 hour)
  maxConcurrentCalculations: 100 # Maximum concurrent calculations
  timeout: 5000                # Calculation timeout (5 seconds)
```

## Adding New Calculation Types

### Example: Mortgage Calculator

```yaml
calculations:
  mortgage:
    type: "mortgage-calculator"
    description: "UK Mortgage Affordability Calculator"
    version: "1.0"
    methods:
      - name: "affordability"
        displayName: "Affordability Method"
        description: "Calculates maximum mortgage based on income"
        formula: "income * 4.5"
        explanation: "Standard UK affordability calculation"
      
      - name: "repayment"
        displayName: "Repayment Method"
        description: "Calculates monthly mortgage payments"
        formula: "principal * rate * (1 + rate)^term / ((1 + rate)^term - 1)"
        explanation: "Standard mortgage repayment formula"
    
    validation:
      required:
        - "income"
        - "deposit"
        - "propertyValue"
        - "interestRate"
        - "term"
      ranges:
        income:
          min: 0
          message: "Income must be positive"
        propertyValue:
          min: 0
          message: "Property value must be positive"
    
    recommendation:
      weights:
        affordability: 0.6
        repayment: 0.4
      limits:
        minimum: 50000
        maximum: 2000000
```

## Runtime Configuration Updates

The configuration can be updated at runtime using Spring Cloud Config or by reloading the configuration file:

```java
@RefreshScope
@Component
public class CalculationConfig {
    // Configuration will be reloaded when @RefreshScope is triggered
}
```

## Testing Configuration Changes

### Unit Testing

```java
@Test
public void testConfigurationChange() {
    // Test with different configuration values
    CalculationConfig config = loadTestConfig();
    ConfigurableCalculationService service = new ConfigurableCalculationService(config);
    
    InsuranceCalculationRequest request = createTestRequest();
    InsuranceCalculationResponse response = service.calculateInsurance(request);
    
    // Verify results match expected values
    assertEquals(expectedCoverage, response.getRecommendedCoverage(), 0.01);
}
```

### A/B Testing

```java
@Service
public class ABTestCalculationService {
    
    @Autowired
    private CalculationConfig configA;
    
    @Autowired
    private CalculationConfig configB;
    
    public ComparisonResult compareCalculations(InsuranceCalculationRequest request) {
        InsuranceCalculationResponse resultA = calculateWithConfig(request, configA);
        InsuranceCalculationResponse resultB = calculateWithConfig(request, configB);
        
        return new ComparisonResult(resultA, resultB);
    }
}
```

## Monitoring and Metrics

### Configuration Metrics

```java
@Component
public class ConfigurationMetrics {
    
    @EventListener
    public void onConfigurationChange(ConfigurationChangeEvent event) {
        // Log configuration changes
        log.info("Configuration updated: {}", event.getChanges());
        
        // Update metrics
        meterRegistry.counter("configuration.changes").increment();
    }
}
```

### Performance Monitoring

```java
@Timed("calculation.duration")
@Cacheable("insuranceCalculations")
public InsuranceCalculationResponse calculateInsurance(InsuranceCalculationRequest request) {
    // Calculation logic
}
```

## Best Practices

### 1. Configuration Validation

Always validate configuration changes:

```java
@Component
public class ConfigurationValidator {
    
    public void validateConfiguration(CalculationConfig config) {
        // Validate required fields
        // Validate ranges and limits
        // Validate formula syntax
        // Validate factor table completeness
    }
}
```

### 2. Version Control

- Keep configuration files in version control
- Use environment-specific configuration files
- Document all configuration changes

### 3. Testing

- Test configuration changes in staging environment
- Use automated tests to verify calculation accuracy
- Perform regression testing after configuration updates

### 4. Monitoring

- Monitor calculation performance after configuration changes
- Track calculation accuracy and user feedback
- Set up alerts for configuration errors

## Troubleshooting

### Common Issues

1. **Configuration Not Loading**
   - Check YAML syntax
   - Verify file path and permissions
   - Check Spring Boot configuration properties

2. **Calculation Results Unexpected**
   - Verify factor table values
   - Check formula syntax
   - Validate input data ranges

3. **Performance Issues**
   - Review caching configuration
   - Check calculation complexity
   - Monitor memory usage

### Debug Mode

Enable debug logging for configuration:

```yaml
logging:
  level:
    com.lloyds.insurance.config: DEBUG
    com.lloyds.insurance.service.ConfigurableCalculationService: DEBUG
```

## Migration Guide

### From Hard-coded to Configuration

1. **Extract Business Logic**
   - Move calculation formulas to configuration
   - Externalize validation rules
   - Configure premium factors

2. **Update Service Layer**
   - Use ConfigurableCalculationService
   - Maintain backward compatibility
   - Add configuration validation

3. **Test Thoroughly**
   - Verify same results as original implementation
   - Test edge cases and validation
   - Performance testing

4. **Deploy Gradually**
   - Use feature flags
   - A/B test with original implementation
   - Monitor for issues

This configuration-driven architecture provides maximum flexibility while maintaining the exact same results as the original implementation. 