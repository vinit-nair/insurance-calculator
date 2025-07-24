package com.lloyds.insurance.controller;

import com.lloyds.insurance.dto.InsuranceCalculationRequest;
import com.lloyds.insurance.dto.InsuranceCalculationResponse;
import com.lloyds.insurance.service.InsuranceCalculationService;
import com.lloyds.insurance.service.ConfigurationTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class InsuranceController {

    @Autowired
    private InsuranceCalculationService insuranceCalculationService;
    
    @Autowired
    private ConfigurationTestService configurationTestService;

    @PostMapping("/calculate")
    public ResponseEntity<InsuranceCalculationResponse> calculateInsurance(@Valid @RequestBody InsuranceCalculationRequest request) {
        InsuranceCalculationResponse response = insuranceCalculationService.calculateInsurance(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Insurance Calculator Service is running");
    }
    
    @GetMapping("/config-test")
    public ResponseEntity<String> testConfiguration() {
        String result = configurationTestService.testConfiguration();
        return ResponseEntity.ok(result);
    }
} 