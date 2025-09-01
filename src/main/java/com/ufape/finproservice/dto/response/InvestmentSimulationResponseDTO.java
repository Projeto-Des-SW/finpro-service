package com.ufape.finproservice.dto.response;

import lombok.Builder;

@Builder
public record InvestmentSimulationResponseDTO(
        double valorFinal,
        double totalInvestido,
        double rendimentoTotal,
        double taxaEfetivaAnual,
        double taxaEfetivaMensal
) { }
