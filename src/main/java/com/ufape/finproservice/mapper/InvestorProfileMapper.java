package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.InvestorProfileResponseDTO;
import com.ufape.finproservice.model.InvestorProfile;

public class InvestorProfileMapper {
    
    public static InvestorProfileResponseDTO toResponseDTO(InvestorProfile entity) {
        return InvestorProfileResponseDTO.builder()
            .id(entity.getId())
            .riskProfile(entity.getRiskProfile())
            .score(entity.getScore())
            .riskTolerance(entity.getRiskTolerance())
            .investmentTerm(entity.getInvestmentTerm())
            .knowledgeLevel(entity.getKnowledgeLevel())
            .createdAt(entity.getCreatedAt())
            .userId(entity.getUser().getId())
            .build();
    }
}
