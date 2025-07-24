package com.lloyds.insurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class InsuranceCalculationRequest {
    
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be between 18 and 80")
    @Max(value = 80, message = "Age must be between 18 and 80")
    private Integer age;
    
    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(male|female)$", message = "Gender must be 'male' or 'female'")
    private String gender;
    
    @NotBlank(message = "Smoking status is required")
    @Pattern(regexp = "^(non-smoker|smoker|ex-smoker)$", message = "Invalid smoking status")
    private String smoking;
    
    @NotNull(message = "Annual income is required")
    @DecimalMin(value = "0.0", message = "Annual income must be positive")
    @JsonProperty("annualIncome")
    private Double annualIncome;
    
    @NotNull(message = "Monthly expenses is required")
    @DecimalMin(value = "0.0", message = "Monthly expenses must be positive")
    @JsonProperty("monthlyExpenses")
    private Double monthlyExpenses;
    
    @DecimalMin(value = "0.0", message = "Existing coverage must be positive")
    @JsonProperty("existingCoverage")
    private Double existingCoverage = 0.0;
    
    @DecimalMin(value = "0.0", message = "Savings must be positive")
    private Double savings = 0.0;
    
    @DecimalMin(value = "0.0", message = "Total debts must be positive")
    @JsonProperty("totalDebts")
    private Double totalDebts = 0.0;
    
    @DecimalMin(value = "0.0", message = "Funeral costs must be positive")
    @JsonProperty("funeralCosts")
    private Double funeralCosts = 4000.0;
    
    @Min(value = 0, message = "Dependents must be positive")
    @Max(value = 10, message = "Maximum 10 dependents allowed")
    private Integer dependents = 0;
    
    @DecimalMin(value = "0.0", message = "Education fund must be positive")
    @JsonProperty("educationFund")
    private Double educationFund = 0.0;
    
    @NotBlank(message = "Coverage period is required")
    @Pattern(regexp = "^(10|15|20|25|30|whole)$", message = "Invalid coverage period")
    @JsonProperty("coveragePeriod")
    private String coveragePeriod;
    
    @Pattern(regexp = "^(yes|no)$", message = "Inflation protection must be 'yes' or 'no'")
    @JsonProperty("inflationProtection")
    private String inflationProtection = "no";

    // Default constructor
    public InsuranceCalculationRequest() {}

    // Getters and Setters
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public Double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(Double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public Double getExistingCoverage() {
        return existingCoverage;
    }

    public void setExistingCoverage(Double existingCoverage) {
        this.existingCoverage = existingCoverage;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Double getTotalDebts() {
        return totalDebts;
    }

    public void setTotalDebts(Double totalDebts) {
        this.totalDebts = totalDebts;
    }

    // Backward compatibility methods - these will be removed in future versions
    @Deprecated
    public Double getMortgage() {
        return totalDebts * 0.7; // Assume 70% is mortgage for backward compatibility
    }

    @Deprecated
    public void setMortgage(Double mortgage) {
        // This method is deprecated and does nothing
    }

    @Deprecated
    public Double getOtherDebts() {
        return totalDebts * 0.3; // Assume 30% is other debts for backward compatibility
    }

    @Deprecated
    public void setOtherDebts(Double otherDebts) {
        // This method is deprecated and does nothing
    }

    @Deprecated
    public Double getSpouseIncome() {
        return 0.0; // Default to 0 for backward compatibility
    }

    @Deprecated
    public void setSpouseIncome(Double spouseIncome) {
        // This method is deprecated and does nothing
    }

    public Double getFuneralCosts() {
        return funeralCosts;
    }

    public void setFuneralCosts(Double funeralCosts) {
        this.funeralCosts = funeralCosts;
    }

    public Integer getDependents() {
        return dependents;
    }

    public void setDependents(Integer dependents) {
        this.dependents = dependents;
    }

    public Double getEducationFund() {
        return educationFund;
    }

    public void setEducationFund(Double educationFund) {
        this.educationFund = educationFund;
    }

    public String getCoveragePeriod() {
        return coveragePeriod;
    }

    public void setCoveragePeriod(String coveragePeriod) {
        this.coveragePeriod = coveragePeriod;
    }

    public String getInflationProtection() {
        return inflationProtection;
    }

    public void setInflationProtection(String inflationProtection) {
        this.inflationProtection = inflationProtection;
    }
} 