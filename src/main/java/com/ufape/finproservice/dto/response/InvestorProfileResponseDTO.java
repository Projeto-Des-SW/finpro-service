package com.ufape.finproservice.dto.response;

import java.time.LocalDateTime;

import com.ufape.finproservice.enumeration.InvestmentTerm;
import com.ufape.finproservice.enumeration.KnowledgeLevel;
import com.ufape.finproservice.enumeration.RiskProfile;
import com.ufape.finproservice.enumeration.RiskTolerance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvestorProfileResponseDTO {
    private Long id;
    private RiskProfile riskProfile;
    private Integer score;
    private RiskTolerance riskTolerance;
    private InvestmentTerm investmentTerm;
    private KnowledgeLevel knowledgeLevel;
    private LocalDateTime createdAt;
    private Long userId;
}
