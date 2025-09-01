package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.IncomeDTO;
import com.ufape.finproservice.dto.response.IncomeResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.IncomeMapper;
import com.ufape.finproservice.model.Income;
import com.ufape.finproservice.model.IncomeCategory;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.IncomeCategoryRepository;
import com.ufape.finproservice.repository.IncomeRepository;
import com.ufape.finproservice.util.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.ufape.finproservice.specification.IncomeSpecifications.*;

@Service
@AllArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final CurrentUserService currentUserService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public IncomeResponseDTO createIncome(IncomeDTO incomeDTO) {
        UserEntity user = currentUserService.getCurrentUser();

        IncomeCategory category = incomeCategoryRepository.findById(incomeDTO.getIncomeCategoryId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_CATEGORY_NOT_FOUND));
        
        Income income = IncomeMapper.toEntity(incomeDTO, user, category);
        Income savedIncome = incomeRepository.save(income);
        return IncomeMapper.toIncomeResponseDTO(savedIncome);
    }

    public IncomeResponseDTO findIncomeById(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_NOT_FOUND));
        
        validateUserOwnership(income);
        return IncomeMapper.toIncomeResponseDTO(income);
    }

    public List<IncomeResponseDTO> findAllUserIncomes() {
        UserEntity user = currentUserService.getCurrentUser();
        return incomeRepository.findByUserId(user.getId()).stream()
                .map(IncomeMapper::toIncomeResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeResponseDTO> findIncomesByPeriod(String startDateStr, String endDateStr) {
        UserEntity user = currentUserService.getCurrentUser();
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        
        return incomeRepository.findByUserIdAndDateBetween(user.getId(), startDate, endDate).stream()
                .map(IncomeMapper::toIncomeResponseDTO)
                .collect(Collectors.toList());
    }

    public IncomeResponseDTO updateIncome(Long id, IncomeDTO incomeDTO) {
        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_NOT_FOUND));
        
        validateUserOwnership(existingIncome);

        IncomeCategory category = incomeCategoryRepository.findById(incomeDTO.getIncomeCategoryId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_CATEGORY_NOT_FOUND));
        
        existingIncome.setDate(incomeDTO.getDate());
        existingIncome.setAmount(incomeDTO.getAmount());
        existingIncome.setPaymentOrigin(incomeDTO.getPaymentOrigin());
        existingIncome.setBalanceSource(incomeDTO.getBalanceSource());
        existingIncome.setObservation(incomeDTO.getObservation());
        existingIncome.setIncomeCategory(category);
        
        Income updatedIncome = incomeRepository.save(existingIncome);
        return IncomeMapper.toIncomeResponseDTO(updatedIncome);
    }

    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_NOT_FOUND));
        
        validateUserOwnership(income);
        incomeRepository.delete(income);
    }

    public List<IncomeResponseDTO> findIncomesByCategory(Long categoryId) {

        UserEntity user = currentUserService.getCurrentUser();

        incomeCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_CATEGORY_NOT_FOUND));

        List<Income> incomes = incomeRepository.findByUserIdAndIncomeCategoryId(user.getId(), categoryId);
        return incomes.stream()
                .map(IncomeMapper::toIncomeResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeResponseDTO> findAllIncomes(Long categoryId, LocalDate startDate, LocalDate endDate) {
        UserEntity user = currentUserService.getCurrentUser();

        Specification<Income> spec = Specification.allOf(
                belongsToUser(user),
                hasCategory(categoryId)
        );

        if (startDate != null) {
            spec = spec.and(dateOnOrAfter(startDate));
        }
        if (endDate != null) {
            spec = spec.and(dateOnOrBefore(endDate));
        }

        return incomeRepository.findAll(spec)
                .stream()
                .map(IncomeMapper::toIncomeResponseDTO)
                .toList();
    }

    private void validateUserOwnership(Income income) {
        UserEntity currentUser = currentUserService.getCurrentUser();
        if (!income.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ExceptionMessage.INCOME_NOT_OWNED);
        }
    }
}
