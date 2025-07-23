package com.lloyds.insurance.dto;

import java.util.List;
import java.util.Map;

public class InsuranceCalculationResponse {
    
    private Double recommendedCoverage;
    private String explanation;
    private PremiumEstimate premiumEstimate;
    private Map<String, CalculationMethod> calculations;
    private List<Recommendation> recommendations;
    private Map<String, Object> dataSources;
    
    public static class PremiumEstimate {
        private Double monthly;
        private Double annual;
        private String explanation;
        
        public PremiumEstimate() {}
        
        public PremiumEstimate(Double monthly, Double annual, String explanation) {
            this.monthly = monthly;
            this.annual = annual;
            this.explanation = explanation;
        }
        
        // Getters and Setters
        public Double getMonthly() {
            return monthly;
        }
        
        public void setMonthly(Double monthly) {
            this.monthly = monthly;
        }
        
        public Double getAnnual() {
            return annual;
        }
        
        public void setAnnual(Double annual) {
            this.annual = annual;
        }
        
        public String getExplanation() {
            return explanation;
        }
        
        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
    }
    
    public static class CalculationMethod {
        private Double amount;
        private Double adjustedAmount;
        private String formula;
        private String explanation;
        private Map<String, Double> breakdown;
        
        public CalculationMethod() {}
        
        public CalculationMethod(Double amount, Double adjustedAmount, String formula, String explanation) {
            this.amount = amount;
            this.adjustedAmount = adjustedAmount;
            this.formula = formula;
            this.explanation = explanation;
        }
        
        // Getters and Setters
        public Double getAmount() {
            return amount;
        }
        
        public void setAmount(Double amount) {
            this.amount = amount;
        }
        
        public Double getAdjustedAmount() {
            return adjustedAmount;
        }
        
        public void setAdjustedAmount(Double adjustedAmount) {
            this.adjustedAmount = adjustedAmount;
        }
        
        public String getFormula() {
            return formula;
        }
        
        public void setFormula(String formula) {
            this.formula = formula;
        }
        
        public String getExplanation() {
            return explanation;
        }
        
        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
        
        public Map<String, Double> getBreakdown() {
            return breakdown;
        }
        
        public void setBreakdown(Map<String, Double> breakdown) {
            this.breakdown = breakdown;
        }
    }
    
    public static class Recommendation {
        private String title;
        private String description;
        
        public Recommendation() {}
        
        public Recommendation(String title, String description) {
            this.title = title;
            this.description = description;
        }
        
        // Getters and Setters
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
    }
    
    // Default constructor
    public InsuranceCalculationResponse() {}
    
    // Getters and Setters
    public Double getRecommendedCoverage() {
        return recommendedCoverage;
    }
    
    public void setRecommendedCoverage(Double recommendedCoverage) {
        this.recommendedCoverage = recommendedCoverage;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public PremiumEstimate getPremiumEstimate() {
        return premiumEstimate;
    }
    
    public void setPremiumEstimate(PremiumEstimate premiumEstimate) {
        this.premiumEstimate = premiumEstimate;
    }
    
    public Map<String, CalculationMethod> getCalculations() {
        return calculations;
    }
    
    public void setCalculations(Map<String, CalculationMethod> calculations) {
        this.calculations = calculations;
    }
    
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
    
    public Map<String, Object> getDataSources() {
        return dataSources;
    }
    
    public void setDataSources(Map<String, Object> dataSources) {
        this.dataSources = dataSources;
    }
} 