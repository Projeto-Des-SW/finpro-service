package com.ufape.finproservice.model;

import java.time.LocalDateTime;

import com.ufape.finproservice.enumeration.InvestmentTerm;
import com.ufape.finproservice.enumeration.KnowledgeLevel;
import com.ufape.finproservice.enumeration.RiskProfile;
import com.ufape.finproservice.enumeration.RiskTolerance;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "investor_profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private RiskProfile riskProfile;
    
    private Integer score;
    
    @Enumerated(EnumType.STRING)
    private RiskTolerance riskTolerance;
    
    @Enumerated(EnumType.STRING)
    private InvestmentTerm investmentTerm;

    @Enumerated(EnumType.STRING)
    private KnowledgeLevel knowledgeLevel;
    
    private LocalDateTime createdAt;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}