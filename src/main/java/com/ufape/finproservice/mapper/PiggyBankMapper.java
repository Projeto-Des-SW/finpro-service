package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.PiggyBankDTO;
import com.ufape.finproservice.dto.PiggyBankResponseDTO;
import com.ufape.finproservice.model.PiggyBank;
import com.ufape.finproservice.model.UserEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PiggyBankMapper {

    public static PiggyBank toEntity(PiggyBankDTO dto, UserEntity user) {
        if (dto == null) return null;

        return PiggyBank.builder()
                .name(dto.getName())
                .status(dto.getStatus())
                .monthlyDeposit(dto.getMonthlyDeposit())
                .savingsGoal(dto.getSavingsGoal())
                .targetDate(dto.getTargetDate())
                .currentAmount(dto.getCurrentAmount() != null ? dto.getCurrentAmount() : BigDecimal.ZERO)
                .depositDay(dto.getDepositDay())
                .user(user)
                .build();
    }

    public static PiggyBankResponseDTO toPiggyBankResponseDTO(PiggyBank piggyBank) {
        if (piggyBank == null) return null;

        BigDecimal progressPercentage = BigDecimal.ZERO;
        if (piggyBank.getSavingsGoal() != null && piggyBank.getSavingsGoal().compareTo(BigDecimal.ZERO) > 0) {
            progressPercentage = piggyBank.getCurrentAmount()
                    .divide(piggyBank.getSavingsGoal(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        return PiggyBankResponseDTO.builder()
                .piggyBankId(piggyBank.getPiggyBankId())
                .name(piggyBank.getName())
                .monthlyDeposit(piggyBank.getMonthlyDeposit())
                .savingsGoal(piggyBank.getSavingsGoal())
                .targetDate(piggyBank.getTargetDate())
                .status(piggyBank.getStatus())
                .currentAmount(piggyBank.getCurrentAmount())
                .depositDay(piggyBank.getDepositDay())
                .progressPercentage(progressPercentage)
                .lastDepositDate(piggyBank.getLastDepositDate())
                .build();
    }
}
