package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ExpenseResponseDTO {
    private Long id;
    private String category;
    private BigDecimal amount;
    private String paymentDestination;
    private String balanceSource;
    private LocalDate date;
    private String observation;
    private Long userId;
    private String userName;
}

