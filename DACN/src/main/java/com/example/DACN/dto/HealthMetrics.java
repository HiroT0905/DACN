package com.example.DACN.dto;

import lombok.Data;

@Data
public class HealthMetrics {
    public boolean hasDonatedBefore;
    public boolean hasChronicDiseases;
    public boolean hasRecentDiseases;
    public boolean hasSymptoms;
    public boolean isPregnantOrNursing;
    public boolean HIVTestAgreement;
}
