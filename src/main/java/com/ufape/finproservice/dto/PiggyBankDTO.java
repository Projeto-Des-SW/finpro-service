package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
public class PiggyBankDTO {
    private String name;
    private String status;
    private BigDecimal monthlyDeposit;
    private BigDecimal savingsGoal;
    private LocalDate targetDate;
}
