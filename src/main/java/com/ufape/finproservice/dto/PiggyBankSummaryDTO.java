package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PiggyBankSummaryDTO {
    private BigDecimal totalSaved;
    private BigDecimal totalGoal;
    private long completedPiggyBanks;
    private long totalPiggyBanks;
    private BigDecimal overallProgressPercentage;
}
