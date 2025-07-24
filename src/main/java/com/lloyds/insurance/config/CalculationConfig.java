package com.lloyds.insurance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@Configuration
@ConfigurationProperties(prefix = "calculations")
public class CalculationConfig {
    
    private Map<String, CalculationType> calculations;
    
    public Map<String, CalculationType> getCalculations() {
        return calculations;
    }
    
    public void setCalculations(Map<String, CalculationType> calculations) {
        this.calculations = calculations;
    }
    
    public static class CalculationType {
        private String type;
        private String description;
        private String version;
        private List<CalculationMethod> methods;
        private ValidationRules validation;
        private PremiumConfig premium;
        private RecommendationConfig recommendation;
        private List<DataSource> dataSources;
        private PerformanceConfig performance;
        
        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        
        public List<CalculationMethod> getMethods() { return methods; }
        public void setMethods(List<CalculationMethod> methods) { this.methods = methods; }
        
        public ValidationRules getValidation() { return validation; }
        public void setValidation(ValidationRules validation) { this.validation = validation; }
        
        public PremiumConfig getPremium() { return premium; }
        public void setPremium(PremiumConfig premium) { this.premium = premium; }
        
        public RecommendationConfig getRecommendation() { return recommendation; }
        public void setRecommendation(RecommendationConfig recommendation) { this.recommendation = recommendation; }
        
        public List<DataSource> getDataSources() { return dataSources; }
        public void setDataSources(List<DataSource> dataSources) { this.dataSources = dataSources; }
        
        public PerformanceConfig getPerformance() { return performance; }
        public void setPerformance(PerformanceConfig performance) { this.performance = performance; }
    }
    
    public static class CalculationMethod {
        private String name;
        private String displayName;
        private String description;
        private String formula;
        private Map<String, Object> multiplier;
        private Map<String, Object> incomeYears;
        private Map<String, Object> yearsOfSupport;
        private Map<String, Object> workingYears;
        private Double discountRate;
        private String explanation;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getFormula() { return formula; }
        public void setFormula(String formula) { this.formula = formula; }
        
        public Map<String, Object> getMultiplier() { return multiplier; }
        public void setMultiplier(Map<String, Object> multiplier) { this.multiplier = multiplier; }
        
        public Map<String, Object> getIncomeYears() { return incomeYears; }
        public void setIncomeYears(Map<String, Object> incomeYears) { this.incomeYears = incomeYears; }
        
        public Map<String, Object> getYearsOfSupport() { return yearsOfSupport; }
        public void setYearsOfSupport(Map<String, Object> yearsOfSupport) { this.yearsOfSupport = yearsOfSupport; }
        
        public Map<String, Object> getWorkingYears() { return workingYears; }
        public void setWorkingYears(Map<String, Object> workingYears) { this.workingYears = workingYears; }
        
        public Double getDiscountRate() { return discountRate; }
        public void setDiscountRate(Double discountRate) { this.discountRate = discountRate; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
    }
    
    public static class ValidationRules {
        private List<String> required;
        private Map<String, Range> ranges;
        private Map<String, Pattern> patterns;
        private Map<String, Object> defaults;
        
        // Getters and Setters
        public List<String> getRequired() { return required; }
        public void setRequired(List<String> required) { this.required = required; }
        
        public Map<String, Range> getRanges() { return ranges; }
        public void setRanges(Map<String, Range> ranges) { this.ranges = ranges; }
        
        public Map<String, Pattern> getPatterns() { return patterns; }
        public void setPatterns(Map<String, Pattern> patterns) { this.patterns = patterns; }
        
        public Map<String, Object> getDefaults() { return defaults; }
        public void setDefaults(Map<String, Object> defaults) { this.defaults = defaults; }
    }
    
    public static class Range {
        private Double min;
        private Double max;
        private String message;
        
        // Getters and Setters
        public Double getMin() { return min; }
        public void setMin(Double min) { this.min = min; }
        
        public Double getMax() { return max; }
        public void setMax(Double max) { this.max = max; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class Pattern {
        private String regex;
        private String message;
        
        // Getters and Setters
        public String getRegex() { return regex; }
        public void setRegex(String regex) { this.regex = regex; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class PremiumConfig {
        private Double baseRate;
        private Map<String, Factor> factors;
        private Map<String, Map<String, Double>> factorTables;
        
        // Getters and Setters
        public Double getBaseRate() { return baseRate; }
        public void setBaseRate(Double baseRate) { this.baseRate = baseRate; }
        
        public Map<String, Factor> getFactors() { return factors; }
        public void setFactors(Map<String, Factor> factors) { this.factors = factors; }
        
        public Map<String, Map<String, Double>> getFactorTables() { return factorTables; }
        public void setFactorTables(Map<String, Map<String, Double>> factorTables) { this.factorTables = factorTables; }
    }
    
    public static class Factor {
        private String source;
        private String description;
        
        // Getters and Setters
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class RecommendationConfig {
        private Map<String, Double> weights;
        private Limits limits;
        private Rounding rounding;
        
        // Getters and Setters
        public Map<String, Double> getWeights() { return weights; }
        public void setWeights(Map<String, Double> weights) { this.weights = weights; }
        
        public Limits getLimits() { return limits; }
        public void setLimits(Limits limits) { this.limits = limits; }
        
        public Rounding getRounding() { return rounding; }
        public void setRounding(Rounding rounding) { this.rounding = rounding; }
    }
    
    public static class Limits {
        private Double minimum;
        private Double maximum;
        
        // Getters and Setters
        public Double getMinimum() { return minimum; }
        public void setMinimum(Double minimum) { this.minimum = minimum; }
        
        public Double getMaximum() { return maximum; }
        public void setMaximum(Double maximum) { this.maximum = maximum; }
    }
    
    public static class Rounding {
        private String method;
        private Integer increment;
        
        // Getters and Setters
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
        
        public Integer getIncrement() { return increment; }
        public void setIncrement(Integer increment) { this.increment = increment; }
    }
    
    public static class DataSource {
        private String name;
        private String description;
        private String url;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
    
    public static class PerformanceConfig {
        private Boolean cacheEnabled;
        private Integer cacheTTL;
        private Integer maxConcurrentCalculations;
        private Integer timeout;
        
        // Getters and Setters
        public Boolean getCacheEnabled() { return cacheEnabled; }
        public void setCacheEnabled(Boolean cacheEnabled) { this.cacheEnabled = cacheEnabled; }
        
        public Integer getCacheTTL() { return cacheTTL; }
        public void setCacheTTL(Integer cacheTTL) { this.cacheTTL = cacheTTL; }
        
        public Integer getMaxConcurrentCalculations() { return maxConcurrentCalculations; }
        public void setMaxConcurrentCalculations(Integer maxConcurrentCalculations) { this.maxConcurrentCalculations = maxConcurrentCalculations; }
        
        public Integer getTimeout() { return timeout; }
        public void setTimeout(Integer timeout) { this.timeout = timeout; }
    }
} 