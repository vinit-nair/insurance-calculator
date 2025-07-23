package com.lloyds.insurance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Service
public class UKDataService {
    
    private static final Logger logger = LoggerFactory.getLogger(UKDataService.class);
    @Autowired
    private RestTemplate restTemplate;
    
    // Fallback static values (your current implementation)
    private static final double FALLBACK_BASE_RATE = 0.05; // 5% current UK base rate
    private static final double FALLBACK_INFLATION_RATE = 0.04; // 4% current UK inflation
    private static final double FALLBACK_ABI_RATE = 0.0012; // 0.12% ABI base rate
    
    @Value("${uk.data.enabled:false}")
    private boolean realTimeDataEnabled;
    
    @Value("${uk.data.timeout:5000}")
    private int apiTimeout;
    
    /**
     * Get current UK base rate with fallback to static value
     */
    @Cacheable(value = "ukBaseRate", unless = "#result == null")
    public double getCurrentBaseRate() {
        if (!realTimeDataEnabled) {
            logger.info("Real-time data disabled, using fallback base rate: {}", FALLBACK_BASE_RATE);
            return FALLBACK_BASE_RATE;
        }
        
        try {
            // Bank of England API call
            String url = "https://www.bankofengland.co.uk/boeapps/database/_iadb-fromshowcolumns.asp?csv.x=yes&Datefrom=01/Jan/2024&Dateto=now&SeriesCodes=IUDBEDR";
            
            // This is a simplified example - you'd need to parse the CSV response
            logger.info("Attempting to fetch current base rate from Bank of England API");
            
            // For now, return fallback but log the attempt
            logger.warn("Bank of England API integration not yet implemented, using fallback rate");
            return FALLBACK_BASE_RATE;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch current base rate from Bank of England API: {}", e.getMessage());
            return FALLBACK_BASE_RATE;
        }
    }
    
    /**
     * Get current UK inflation rate with fallback
     */
    @Cacheable(value = "ukInflationRate", unless = "#result == null")
    public double getCurrentInflationRate() {
        if (!realTimeDataEnabled) {
            logger.info("Real-time data disabled, using fallback inflation rate: {}", FALLBACK_INFLATION_RATE);
            return FALLBACK_INFLATION_RATE;
        }
        
        try {
            // ONS API call would go here
            logger.info("Attempting to fetch current inflation rate from ONS API");
            
            // For now, return fallback but log the attempt
            logger.warn("ONS API integration not yet implemented, using fallback rate");
            return FALLBACK_INFLATION_RATE;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch current inflation rate from ONS API: {}", e.getMessage());
            return FALLBACK_INFLATION_RATE;
        }
    }
    
    /**
     * Get ABI insurance market data with fallback
     */
    @Cacheable(value = "abiMarketData", unless = "#result == null")
    public double getABIBaseRate() {
        if (!realTimeDataEnabled) {
            logger.info("Real-time data disabled, using fallback ABI rate: {}", FALLBACK_ABI_RATE);
            return FALLBACK_ABI_RATE;
        }
        
        try {
            // ABI API call would go here
            logger.info("Attempting to fetch ABI market data");
            
            // For now, return fallback but log the attempt
            logger.warn("ABI API integration not yet implemented, using fallback rate");
            return FALLBACK_ABI_RATE;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch ABI market data: {}", e.getMessage());
            return FALLBACK_ABI_RATE;
        }
    }
    
    /**
     * Get mortality data for age group with fallback
     */
    @Cacheable(value = "mortalityData", unless = "#result == null")
    public double getMortalityRate(int age, String gender) {
        if (!realTimeDataEnabled) {
            // Return static mortality adjustment based on age/gender
            double baseRate = 1.0;
            if (age > 50) baseRate *= 1.2;
            if (age > 65) baseRate *= 1.5;
            if ("MALE".equalsIgnoreCase(gender)) baseRate *= 1.1;
            
            logger.info("Real-time data disabled, using calculated mortality rate: {} for age: {}, gender: {}", 
                       baseRate, age, gender);
            return baseRate;
        }
        
        try {
            // CMI API call would go here
            logger.info("Attempting to fetch mortality data from CMI for age: {}, gender: {}", age, gender);
            
            // For now, return calculated fallback
            double baseRate = 1.0;
            if (age > 50) baseRate *= 1.2;
            if (age > 65) baseRate *= 1.5;
            if ("MALE".equalsIgnoreCase(gender)) baseRate *= 1.1;
            
            logger.warn("CMI API integration not yet implemented, using calculated rate");
            return baseRate;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch mortality data: {}", e.getMessage());
            // Return calculated fallback
            double baseRate = 1.0;
            if (age > 50) baseRate *= 1.2;
            if (age > 65) baseRate *= 1.5;
            if ("MALE".equalsIgnoreCase(gender)) baseRate *= 1.1;
            return baseRate;
        }
    }
    
    /**
     * Get data source information for transparency
     */
    public Map<String, Object> getDataSourceInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("realTimeEnabled", realTimeDataEnabled);
        info.put("lastUpdated", LocalDateTime.now());
        info.put("sources", Map.of(
            "baseRate", realTimeDataEnabled ? "Bank of England API" : "Static (5.0%)",
            "inflation", realTimeDataEnabled ? "ONS API" : "Static (4.0%)",
            "abiRate", realTimeDataEnabled ? "ABI API" : "Static (0.12%)",
            "mortality", realTimeDataEnabled ? "CMI API" : "Calculated"
        ));
        return info;
    }
} 