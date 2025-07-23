package com.lloyds.insurance.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class CalculationProgress {
    
    private String sessionId;
    private int currentStep;
    private int totalSteps;
    private String currentStepName;
    private String currentStepDescription;
    private double progressPercentage;
    private LocalDateTime startTime;
    private LocalDateTime lastUpdateTime;
    private List<ProgressStep> completedSteps;
    private boolean isComplete;
    private String status; // "STARTING", "IN_PROGRESS", "COMPLETED", "ERROR"
    
    public static class ProgressStep {
        private String name;
        private String description;
        private LocalDateTime completedAt;
        private long durationMs;
        private boolean success;
        
        public ProgressStep() {}
        
        public ProgressStep(String name, String description, LocalDateTime completedAt, long durationMs, boolean success) {
            this.name = name;
            this.description = description;
            this.completedAt = completedAt;
            this.durationMs = durationMs;
            this.success = success;
        }
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public LocalDateTime getCompletedAt() { return completedAt; }
        public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
        
        public long getDurationMs() { return durationMs; }
        public void setDurationMs(long durationMs) { this.durationMs = durationMs; }
        
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }
    
    public CalculationProgress() {
        this.completedSteps = new ArrayList<>();
        this.startTime = LocalDateTime.now();
        this.lastUpdateTime = LocalDateTime.now();
        this.status = "STARTING";
    }
    
    public CalculationProgress(String sessionId, int totalSteps) {
        this();
        this.sessionId = sessionId;
        this.totalSteps = totalSteps;
        this.currentStep = 0;
        this.progressPercentage = 0.0;
    }
    
    public void updateProgress(int step, String stepName, String stepDescription) {
        this.currentStep = step;
        this.currentStepName = stepName;
        this.currentStepDescription = stepDescription;
        this.progressPercentage = ((double) step / totalSteps) * 100.0;
        this.lastUpdateTime = LocalDateTime.now();
        this.status = step >= totalSteps ? "COMPLETED" : "IN_PROGRESS";
        this.isComplete = step >= totalSteps;
    }
    
    public void addCompletedStep(String name, String description, long durationMs, boolean success) {
        ProgressStep step = new ProgressStep(name, description, LocalDateTime.now(), durationMs, success);
        this.completedSteps.add(step);
    }
    
    // Getters and Setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public int getCurrentStep() { return currentStep; }
    public void setCurrentStep(int currentStep) { this.currentStep = currentStep; }
    
    public int getTotalSteps() { return totalSteps; }
    public void setTotalSteps(int totalSteps) { this.totalSteps = totalSteps; }
    
    public String getCurrentStepName() { return currentStepName; }
    public void setCurrentStepName(String currentStepName) { this.currentStepName = currentStepName; }
    
    public String getCurrentStepDescription() { return currentStepDescription; }
    public void setCurrentStepDescription(String currentStepDescription) { this.currentStepDescription = currentStepDescription; }
    
    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }
    
    public List<ProgressStep> getCompletedSteps() { return completedSteps; }
    public void setCompletedSteps(List<ProgressStep> completedSteps) { this.completedSteps = completedSteps; }
    
    public boolean isComplete() { return isComplete; }
    public void setComplete(boolean complete) { isComplete = complete; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 