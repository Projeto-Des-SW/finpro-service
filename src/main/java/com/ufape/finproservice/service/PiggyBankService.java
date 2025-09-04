package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.*;
import com.ufape.finproservice.dto.response.PiggyBankDepositResponseDTO;
import com.ufape.finproservice.dto.response.PiggyBankResponseDTO;
import com.ufape.finproservice.enumeration.Status;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.PiggyBankMapper;
import com.ufape.finproservice.model.PiggyBank;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.PiggyBankRepository;
import com.ufape.finproservice.util.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PiggyBankService {

    private final PiggyBankRepository piggyBankRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public PiggyBankResponseDTO createPiggyBank(PiggyBankDTO piggyBankDTO) {
        UserEntity user = currentUserService.getCurrentUser();

        if (piggyBankDTO.getTargetDate().isBefore(LocalDate.now())) {
            throw new CustomException(ExceptionMessage.INVALID_DATE);
        }

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

        if (piggyBankDTO.getTargetDate().isBefore(LocalDate.now())) {
            throw new CustomException(ExceptionMessage.INVALID_DATE);
        }

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

    @Transactional
    public void deletePiggyBank(Long id) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        piggyBankRepository.delete(piggyBank);
    }

    @Transactional
    public PiggyBankDepositResponseDTO deposit(Long id, BigDecimal amount) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(ExceptionMessage.INVALID_AMOUNT);
        }

        piggyBank.setCurrentAmount(piggyBank.getCurrentAmount().add(amount));
        piggyBank.setLastDepositDate(LocalDate.now());

        String message;
        if (piggyBank.getCurrentAmount().compareTo(piggyBank.getSavingsGoal()) >= 0) {
            piggyBank.setStatus(Status.COMPLETED);
            message = "Parabéns! Você atingiu sua meta!";
        } else {
            message = "Depósito realizado com sucesso!";
        }

        PiggyBank updatedPiggyBank = piggyBankRepository.save(piggyBank);
        PiggyBankResponseDTO piggyBankResponseDTO = PiggyBankMapper.toPiggyBankResponseDTO(updatedPiggyBank);
        piggyBankResponseDTO.setRecommendedMonthlyDeposit(calculateRecommendedMonthlyDeposit(id));

        return new PiggyBankDepositResponseDTO(piggyBankResponseDTO, amount, message);
    }
    public PiggyBankProgressDTO getPiggyBankProgressDetails(Long id) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        BigDecimal remainingAmount = piggyBank.getSavingsGoal().subtract(piggyBank.getCurrentAmount());
        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            remainingAmount = BigDecimal.ZERO;
        }

        BigDecimal progressPercentage = piggyBank.getCurrentAmount()
                .divide(piggyBank.getSavingsGoal(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));

        Integer remainingMonths = calculateRemainingTime(id);

        PiggyBankResponseDTO piggyBankResponseDTO = PiggyBankMapper.toPiggyBankResponseDTO(piggyBank);

        return new PiggyBankProgressDTO(progressPercentage, remainingMonths, remainingAmount, piggyBankResponseDTO);
    }

    public Integer calculateRemainingTime(Long id) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        LocalDate now = LocalDate.now();
        LocalDate targetDate = piggyBank.getTargetDate();

        if (targetDate == null || targetDate.isBefore(now)) {
            return 0;
        }

        Period period = Period.between(now, targetDate);
        return period.getYears() * 12 + period.getMonths();
    }

    public BigDecimal calculateRecommendedMonthlyDeposit(Long id) {
        UserEntity user = currentUserService.getCurrentUser();
        PiggyBank piggyBank = piggyBankRepository.findByPiggyBankIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PIGGY_BANK_NOT_FOUND));

        if (piggyBank.getSavingsGoal() == null || piggyBank.getCurrentAmount() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal remainingAmount = piggyBank.getSavingsGoal().subtract(piggyBank.getCurrentAmount());
        Integer remainingMonths = calculateRemainingTime(id);

        if (remainingMonths <= 0 || remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return remainingAmount.divide(new BigDecimal(remainingMonths), 2, RoundingMode.CEILING);
    }

    public List<PiggyBankResponseDTO> getDepositReminders() {
        UserEntity user = currentUserService.getCurrentUser();
        int today = LocalDate.now().getDayOfMonth();
        return piggyBankRepository.findByUserId(user.getId()).stream()
                .filter(piggyBank -> piggyBank.getDepositDay() == today)
                .map(PiggyBankMapper::toPiggyBankResponseDTO)
                .collect(Collectors.toList());
    }

    public PiggyBankSummaryDTO getPiggyBankSummary() {
        UserEntity user = currentUserService.getCurrentUser();
        List<PiggyBank> piggyBanks = piggyBankRepository.findByUserId(user.getId());

        BigDecimal totalSaved = piggyBanks.stream()
                .map(PiggyBank::getCurrentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGoal = piggyBanks.stream()
                .map(PiggyBank::getSavingsGoal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long completedPiggyBanks = piggyBanks.stream()
                .filter(p -> p.getStatus() == Status.COMPLETED)
                .count();

        long totalPiggyBanks = piggyBanks.size();

        BigDecimal overallProgressPercentage = BigDecimal.ZERO;
        if (totalGoal.compareTo(BigDecimal.ZERO) > 0) {
            overallProgressPercentage = totalSaved
                    .divide(totalGoal, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        return new PiggyBankSummaryDTO(totalSaved, totalGoal, completedPiggyBanks, totalPiggyBanks, overallProgressPercentage);
    }
}