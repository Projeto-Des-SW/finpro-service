package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.PiggyBankDTO;
import com.ufape.finproservice.dto.PiggyBankResponseDTO;
import com.ufape.finproservice.model.PiggyBank;
import com.ufape.finproservice.model.UserEntity;

public class PiggyBankMapper {

    public static PiggyBank toEntity(PiggyBankDTO dto, UserEntity user) {
        if (dto == null) return null;

        return PiggyBank.builder()
                .name(dto.getName())
                .status(dto.getStatus())
                .monthlyDeposit(dto.getMonthlyDeposit())
                .savingsGoal(dto.getSavingsGoal())
                .targetDate(dto.getTargetDate())
                .user(user)
                .build();
    }

    public static PiggyBankResponseDTO toPiggyBankResponseDTO(PiggyBank piggyBank) {
        if (piggyBank == null) return null;

        return PiggyBankResponseDTO.builder()
                .piggyBankId(piggyBank.getPiggyBankId())
                .name(piggyBank.getName())
                .monthlyDeposit(piggyBank.getMonthlyDeposit())
                .savingsGoal(piggyBank.getSavingsGoal())
                .targetDate(piggyBank.getTargetDate())
                .status(piggyBank.getStatus())
                .build();
    }

}
