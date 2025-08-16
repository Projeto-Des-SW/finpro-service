package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.PiggyBankDTO;
import com.ufape.finproservice.dto.PiggyBankResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.PiggyBankMapper;
import com.ufape.finproservice.model.PiggyBank;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.PiggyBankRespository;
import com.ufape.finproservice.util.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PiggyBankService {

    private final PiggyBankRespository piggyBankRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public PiggyBankResponseDTO createPiggyBank(PiggyBankDTO piggyBankDTO) {
        UserEntity user = currentUserService.getCurrentUser();

        if (piggyBankRepository.existsByNameAndUserId(piggyBankDTO.getName(), user.getId())) {
            throw new CustomException(ExceptionMessage.PIGGY_BANK_ALREADY_EXISTS);
        }

        PiggyBank piggyBank = PiggyBankMapper.toEntity(piggyBankDTO, user);
        PiggyBank savedPiggyBank = piggyBankRepository.save(piggyBank);
        return PiggyBankMapper.toPiggyBankResponseDTO(savedPiggyBank);
    }

    public PiggyBankResponseDTO findPiggyBankById(Long id) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        return PiggyBankMapper.toPiggyBankResponseDTO(piggyBank);
    }

    public List<PiggyBankResponseDTO> findAllUserPiggyBanks() {
        UserEntity user = currentUserService.getCurrentUser();
        return piggyBankRepository.findByUserId(user.getId()).stream()
                .map(PiggyBankMapper::toPiggyBankResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PiggyBankResponseDTO> findPiggyBanksByName(String name) {
        UserEntity user = currentUserService.getCurrentUser();
        return piggyBankRepository.findByNameContainingIgnoreCaseAndUserId(name, user.getId()).stream()
                .map(PiggyBankMapper::toPiggyBankResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PiggyBankResponseDTO updatePiggyBank(Long id, PiggyBankDTO piggyBankDTO) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank existingPiggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        if (!existingPiggyBank.getName().equals(piggyBankDTO.getName()) &&
                piggyBankRepository.existsByNameAndUserId(piggyBankDTO.getName(), user.getId())) {
            throw new CustomException(ExceptionMessage.PIGGY_BANK_ALREADY_EXISTS);
        }

        existingPiggyBank.setName(piggyBankDTO.getName());
        existingPiggyBank.setMonthlyDeposit(piggyBankDTO.getMonthlyDeposit());
        existingPiggyBank.setSavingsGoal(piggyBankDTO.getSavingsGoal());
        existingPiggyBank.setTargetDate(piggyBankDTO.getTargetDate());
        existingPiggyBank.setStatus(piggyBankDTO.getStatus());

        PiggyBank updatedPiggyBank = piggyBankRepository.save(existingPiggyBank);
        return PiggyBankMapper.toPiggyBankResponseDTO(updatedPiggyBank);
    }

/*
    @Transactional
    public void deletePiggyBank(Long id) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        piggyBankRepository.delete(piggyBank);
    }

    private void validateUserOwnership(PiggyBank piggyBank) {
        UserEntity currentUser = currentUserService.getCurrentUser();
        if (!piggyBank.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ExceptionMessage.PIGGY_BANK_NOT_OWNED);
        }
    }

  */
}