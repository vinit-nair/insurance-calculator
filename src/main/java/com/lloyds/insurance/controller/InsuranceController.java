package com.lloyds.insurance.controller;

import com.lloyds.insurance.dto.InsuranceCalculationRequest;
import com.lloyds.insurance.dto.InsuranceCalculationResponse;
import com.lloyds.insurance.service.InsuranceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InsuranceController {

    @Autowired
    private InsuranceCalculationService insuranceCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<InsuranceCalculationResponse> calculateInsurance(@RequestBody InsuranceCalculationRequest request) {
        try {
            InsuranceCalculationResponse response = insuranceCalculationService.calculateInsurance(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Insurance Calculator API is running");
    }
} 