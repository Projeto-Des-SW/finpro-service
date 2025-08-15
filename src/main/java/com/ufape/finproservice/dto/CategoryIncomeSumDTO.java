package com.ufape.finproservice.dto;

import java.math.BigDecimal;

public record CategoryIncomeSumDTO(
    int year, 
    int month,
    Long categoryId,
    String categoryName,
    BigDecimal total
) {}
