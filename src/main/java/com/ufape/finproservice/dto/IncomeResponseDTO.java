package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class IncomeResponseDTO {
    private Long incomeId;
    private LocalDate date;
    //private IncomeCategoryResponseDTO category;
    private BigDecimal amount;
    private String paymentOrigin;
    private String balanceSource;
    private String observation;
    private Long userId;
}
