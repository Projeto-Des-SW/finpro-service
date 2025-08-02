package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ExpenseDTO {
    private LocalDate date;
    private Long expenseCategoryId;
    private BigDecimal amount;
    private String paymentDestination;
    private String balanceSource;
    private String observation;
    private Long userId;
 }

