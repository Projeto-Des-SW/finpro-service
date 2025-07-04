package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String category;
    private BigDecimal amount;
    private String paymentDestination;
    private String balanceSource;
    private LocalDate date;
    private String observation;
    private Long userId;
}

