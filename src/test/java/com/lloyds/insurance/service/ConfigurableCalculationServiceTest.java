package com.lloyds.insurance.service;

import com.lloyds.insurance.config.CalculationConfig;
import com.lloyds.insurance.dto.InsuranceCalculationRequest;
import com.lloyds.insurance.dto.InsuranceCalculationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConfigurableCalculationServiceTest {
    
    @Autowired
    private ConfigurableCalculationService configurableService;
    
    @Autowired
    private CalculationConfig calculationConfig;
    
    @Test
    public void testConfigurationLoaded() {
        assertNotNull(calculationConfig);
        assertNotNull(calculationConfig.getCalculations());
        assertTrue(calculationConfig.getCalculations().containsKey("insurance"));
        
        CalculationConfig.CalculationType insuranceConfig = calculationConfig.getCalculations().get("insurance");
        assertEquals("life-insurance", insuranceConfig.getType());
        assertEquals("1.0", insuranceConfig.getVersion());
        assertNotNull(insuranceConfig.getMethods());
        assertTrue(insuranceConfig.getMethods().size() > 0);
    }
    
    @Test
    public void testBasicCalculation() {
        InsuranceCalculationRequest request = new InsuranceCalculationRequest();
        request.setAge(35);
        request.setGender("male");
        request.setSmoking("non-smoker");
        request.setAnnualIncome(50000.0);
        request.setMonthlyExpenses(2500.0);
        request.setCoveragePeriod("20");
        request.setDependents(2);
        request.setSpouseIncome(30000.0);
        request.setMortgage(200000.0);
        request.setOtherDebts(15000.0);
        request.setEducationFund(25000.0);
        request.setFuneralCosts(5000.0);
        request.setSavings(50000.0);
        request.setExistingCoverage(100000.0);
        request.setInflationProtection("no");
        
        InsuranceCalculationResponse response = configurableService.calculateInsurance(request);
        
        assertNotNull(response);
        assertNotNull(response.getRecommendedCoverage());
        assertTrue(response.getRecommendedCoverage() > 0);
        assertNotNull(response.getExplanation());
        assertNotNull(response.getPremiumEstimate());
        assertNotNull(response.getCalculations());
        assertTrue(response.getCalculations().size() > 0);
        
        // Verify all calculation methods are present
        assertTrue(response.getCalculations().containsKey("income-replacement"));
        assertTrue(response.getCalculations().containsKey("dime"));
        assertTrue(response.getCalculations().containsKey("needs-analysis"));
        assertTrue(response.getCalculations().containsKey("human-life-value"));
    }
} 