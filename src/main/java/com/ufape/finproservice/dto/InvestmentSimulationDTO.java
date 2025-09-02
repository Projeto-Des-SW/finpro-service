package com.ufape.finproservice.dto;

import com.ufape.finproservice.enumeration.InvestmentType;

public record InvestmentSimulationDTO(
        double startValue,
        double monthlyValue,
        int months,
        InvestmentType investmentType,
        double percentInvestmentType
) { }
