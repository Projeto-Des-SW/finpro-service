package com.ufape.finproservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class PiggyBankDepositDTO {
    @NotNull
    @Positive
    private BigDecimal amount;

}
