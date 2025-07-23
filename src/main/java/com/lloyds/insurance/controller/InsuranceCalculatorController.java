package com.lloyds.insurance.controller;

import com.lloyds.insurance.dto.InsuranceCalculationRequest;
import com.lloyds.insurance.dto.InsuranceCalculationResponse;
import com.lloyds.insurance.service.InsuranceCalculationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@Tag(name = "Insurance Calculator", description = "UK Life Insurance Calculator API")
public class InsuranceCalculatorController {
    
    @Autowired
    private InsuranceCalculationService calculationService;
    

    
    @PostMapping("/calculate")
    @Operation(summary = "Calculate life insurance needs", 
               description = "Calculate personalized life insurance recommendations based on UK formulas")
    public ResponseEntity<InsuranceCalculationResponse> calculateInsurance(
            @Valid @RequestBody InsuranceCalculationRequest request) {
        
        try {
            // Add security headers
            InsuranceCalculationResponse response = calculationService.calculateInsurance(request);
            
            return ResponseEntity.ok()
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("X-XSS-Protection", "1; mode=block")
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(response);
                
        } catch (Exception e) {
            // Log error without exposing sensitive information
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("X-XSS-Protection", "1; mode=block")
                .body(null);
        }
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok()
            .header("X-Content-Type-Options", "nosniff")
            .header("X-Frame-Options", "DENY")
            .header("X-XSS-Protection", "1; mode=block")
            .body("Insurance Calculator Service is running");
    }
    

} 