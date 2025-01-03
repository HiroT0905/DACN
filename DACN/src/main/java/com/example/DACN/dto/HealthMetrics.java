package com.example.DACN.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // Đảm bảo chỉ serialize các trường không null
public class HealthMetrics {
    public boolean hasDonatedBefore;
    public boolean hasChronicDiseases;
    public boolean hasRecentDiseases;
    public boolean hasSymptoms;
    public boolean isPregnantOrNursing;
    public boolean HIVTestAgreement;
    public HealthMetrics() {
    }
    public HealthMetrics(Map<String, Boolean> healthMetrics) {
        this.hasDonatedBefore = healthMetrics.getOrDefault("hasDonatedBefore", false);
        this.hasChronicDiseases = healthMetrics.getOrDefault("hasChronicDiseases", false);
        this.hasRecentDiseases = healthMetrics.getOrDefault("hasRecentDiseases", false);
        this.hasSymptoms = healthMetrics.getOrDefault("hasSymptoms", false);
        this.isPregnantOrNursing = healthMetrics.getOrDefault("isPregnantOrNursing", false);
        this.HIVTestAgreement = healthMetrics.getOrDefault("HIVTestAgreement", false);
    }
}
