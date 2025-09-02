package com.ufape.finproservice.dto;

import com.ufape.finproservice.enumeration.RiskProfile;
import lombok.Builder;

import java.util.List;

@Builder
public record InvestmentRecommendationDTO(
        RiskProfile riskProfile,
        List<String> recommendations
) { }
