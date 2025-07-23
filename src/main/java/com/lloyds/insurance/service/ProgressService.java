package com.lloyds.insurance.service;

import com.lloyds.insurance.dto.CalculationProgress;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class ProgressService {
    
    private final Map<String, CalculationProgress> progressMap = new ConcurrentHashMap<>();
    
    public String createProgressSession() {
        String sessionId = UUID.randomUUID().toString();
        CalculationProgress progress = new CalculationProgress(sessionId, 8); // 8 total steps
        progressMap.put(sessionId, progress);
        return sessionId;
    }
    
    public CalculationProgress getProgress(String sessionId) {
        return progressMap.get(sessionId);
    }
    
    public void updateProgress(String sessionId, int step, String stepName, String stepDescription) {
        CalculationProgress progress = progressMap.get(sessionId);
        if (progress != null) {
            progress.updateProgress(step, stepName, stepDescription);
        }
    }
    
    public void addCompletedStep(String sessionId, String name, String description, long durationMs, boolean success) {
        CalculationProgress progress = progressMap.get(sessionId);
        if (progress != null) {
            progress.addCompletedStep(name, description, durationMs, success);
        }
    }
    
    public void completeProgress(String sessionId) {
        CalculationProgress progress = progressMap.get(sessionId);
        if (progress != null) {
            progress.setComplete(true);
            progress.setStatus("COMPLETED");
            progress.setLastUpdateTime(LocalDateTime.now());
        }
    }
    
    public void errorProgress(String sessionId, String errorMessage) {
        CalculationProgress progress = progressMap.get(sessionId);
        if (progress != null) {
            progress.setStatus("ERROR");
            progress.setCurrentStepDescription("Error: " + errorMessage);
            progress.setLastUpdateTime(LocalDateTime.now());
        }
    }
    
    public void cleanupOldSessions() {
        // Remove sessions older than 1 hour
        LocalDateTime cutoff = LocalDateTime.now().minusHours(1);
        progressMap.entrySet().removeIf(entry -> 
            entry.getValue().getStartTime().isBefore(cutoff)
        );
    }
} 