package com.lloyds.insurance.service;

import com.lloyds.insurance.config.CalculationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationTestService {
    
    @Autowired
    private CalculationConfig calculationConfig;
    
    public String testConfiguration() {
        try {
            // Test if configuration loads
            var config = calculationConfig.getCalculations();
            if (config != null && config.containsKey("insurance")) {
                var insuranceConfig = config.get("insurance");
                return "Configuration loaded successfully! Found " + 
                       insuranceConfig.getMethods().size() + " calculation methods.";
            } else {
                return "Configuration loaded but no insurance config found.";
            }
        } catch (Exception e) {
            return "Configuration loading failed: " + e.getMessage();
        }
    }
} 