package com.ufape.finproservice.dto;

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
public class ProfileCalculationResultDTO {
    private Integer totalScore;          // 0-30
    private RiskProfile riskProfile;     // CONSERVATIVE, MODERATE, AGGRESSIVE
    private RiskTolerance riskTolerance; // LOW, MEDIUM, HIGH
    private InvestmentTerm investmentTerm; // SHORT_TERM, MEDIUM_TERM, LONG_TERM
    private KnowledgeLevel knowledgeLevel; // BEGINNER, INTERMEDIATE, ADVANCED
}
