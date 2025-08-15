package com.ufape.finproservice.dto;

import java.math.BigDecimal;

public record MonthlyIncomeSumDTO(int year, int month, BigDecimal total) {}