package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class IncomeDTO {
    private LocalDate date;
    private Long incomeCategoryId;
    private BigDecimal amount;
    private String paymentOrigin;
    private String balanceSource;
    private String observation;
}
