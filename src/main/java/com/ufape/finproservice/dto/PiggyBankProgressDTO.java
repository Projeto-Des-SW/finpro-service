package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PiggyBankProgressDTO {
    private BigDecimal progressPercentage;
    private Integer remainingMonths;
    private BigDecimal remainingAmount;
    private PiggyBankResponseDTO piggyBank;
}
