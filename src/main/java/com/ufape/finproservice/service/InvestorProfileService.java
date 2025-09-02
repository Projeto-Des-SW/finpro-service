package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.InvestorProfileRequestDTO;
import com.ufape.finproservice.dto.response.InvestorProfileResponseDTO;
import com.ufape.finproservice.dto.ProfileCalculationResultDTO;
import com.ufape.finproservice.dto.QuestionAnswerDTO;
import com.ufape.finproservice.dto.response.QuestionnaireResponseDTO;
import com.ufape.finproservice.enumeration.InvestmentTerm;
import com.ufape.finproservice.enumeration.KnowledgeLevel;
import com.ufape.finproservice.enumeration.RiskProfile;
import com.ufape.finproservice.enumeration.RiskTolerance;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.InvestorProfileMapper;
import com.ufape.finproservice.model.InvestorProfile;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.InvestorProfileRepository;
import com.ufape.finproservice.util.CurrentUserService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvestorProfileService {
    
    private final InvestorProfileRepository repository;
    private final CurrentUserService currentUserService;
    
    public ProfileCalculationResultDTO calculateProfile(QuestionnaireResponseDTO questionnaireDTO) {
        int totalScore = calculateTotalScore(questionnaireDTO.getAnswers());
        
        return ProfileCalculationResultDTO.builder()
            .totalScore(totalScore)
            .riskProfile(determineRiskProfile(totalScore))
            .riskTolerance(determineRiskTolerance(totalScore))
            .investmentTerm(determineInvestmentTerm(totalScore))
            .knowledgeLevel(determineKnowledgeLevel(totalScore))
            .build();
    }

    public InvestorProfileResponseDTO getCurrentUserProfile() {
        UserEntity user = currentUserService.getCurrentUser();
        InvestorProfile profile = repository.findByUserId(user.getId())
            .orElseThrow(() -> new CustomException(ExceptionMessage.INVESTOR_PROFILE_NOT_FOUND));
        return InvestorProfileMapper.toResponseDTO(profile);
    }

    public InvestorProfileResponseDTO saveProfile(InvestorProfileRequestDTO requestDTO) {
        UserEntity user = currentUserService.getCurrentUser();
        
        int totalScore = calculateTotalScore(requestDTO.getAnswers());
        RiskProfile riskProfile = determineRiskProfile(totalScore);
        RiskTolerance riskTolerance = determineRiskTolerance(totalScore);
        InvestmentTerm investmentTerm = determineInvestmentTerm(totalScore);
        KnowledgeLevel knowledgeLevel = determineKnowledgeLevel(totalScore);
        
        Optional<InvestorProfile> existingProfile = repository.findByUserId(user.getId());
        InvestorProfile profile;
        
        if (existingProfile.isPresent()) {
            profile = existingProfile.get();
            profile.setRiskProfile(riskProfile);
            profile.setScore(totalScore);
            profile.setRiskTolerance(riskTolerance);
            profile.setInvestmentTerm(investmentTerm);
            profile.setKnowledgeLevel(knowledgeLevel);
        } else {
            profile = InvestorProfile.builder()
                .riskProfile(riskProfile)
                .score(totalScore)
                .riskTolerance(riskTolerance)
                .investmentTerm(investmentTerm)
                .knowledgeLevel(knowledgeLevel)
                .user(user)
                .build();
        }
        
        InvestorProfile savedProfile = repository.save(profile);
        return InvestorProfileMapper.toResponseDTO(savedProfile);
    }
    
    private int calculateTotalScore(List<QuestionAnswerDTO> answers) {
        return answers.stream()
            .mapToInt(QuestionAnswerDTO::getSelectedValue)
            .sum();
    }
    
    private RiskProfile determineRiskProfile(int score) {
        if (score <= 10) return RiskProfile.CONSERVATIVE;
        if (score <= 20) return RiskProfile.MODERATE;
        return RiskProfile.AGGRESSIVE;
    }
    
    private RiskTolerance determineRiskTolerance(int score) {
        if (score <= 10) return RiskTolerance.LOW;
        if (score <= 20) return RiskTolerance.MEDIUM;
        return RiskTolerance.HIGH;
    }
    
    private InvestmentTerm determineInvestmentTerm(int score) {
        if (score <= 10) return InvestmentTerm.SHORT_TERM;
        if (score <= 20) return InvestmentTerm.MEDIUM_TERM;
        return InvestmentTerm.LONG_TERM;
    }

    private KnowledgeLevel determineKnowledgeLevel(int score) {
    if (score <= 10) return KnowledgeLevel.BEGINNER;
    if (score <= 20) return KnowledgeLevel.INTERMEDIATE;
    return KnowledgeLevel.ADVANCED;
    }
}
