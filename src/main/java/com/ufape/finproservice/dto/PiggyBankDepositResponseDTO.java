package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PiggyBankDepositResponseDTO {
    private PiggyBankResponseDTO piggyBank;
    private BigDecimal depositedAmount;
    private String message;
}
