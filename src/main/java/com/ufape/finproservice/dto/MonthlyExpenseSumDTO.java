package com.ufape.finproservice.dto;

import java.math.BigDecimal;

public record MonthlyExpenseSumDTO(int year, int month, BigDecimal total) {}
