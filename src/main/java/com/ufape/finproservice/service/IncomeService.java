package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.IncomeDTO;
import com.ufape.finproservice.dto.IncomeResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.IncomeMapper;
import com.ufape.finproservice.model.Income;
import com.ufape.finproservice.model.IncomeCategory;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.IncomeCategoryRepository;
import com.ufape.finproservice.repository.IncomeRepository;
import com.ufape.finproservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public IncomeResponseDTO createIncome(IncomeDTO incomeDTO) {
        UserEntity user = getCurrentUser();

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
        UserEntity user = getCurrentUser();
        return incomeRepository.findByUserId(user.getId()).stream()
                .map(IncomeMapper::toIncomeResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeResponseDTO> findIncomesByPeriod(String startDateStr, String endDateStr) {
        UserEntity user = getCurrentUser();
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
        existingIncome.setCategory(category);
        
        Income updatedIncome = incomeRepository.save(existingIncome);
        return IncomeMapper.toIncomeResponseDTO(updatedIncome);
    }

    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_NOT_FOUND));
        
        validateUserOwnership(income);
        incomeRepository.delete(income);
    }

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
               .orElseThrow(() -> new CustomException(ExceptionMessage.USER_NOT_FOUND));
    }

    private void validateUserOwnership(Income income) {
        UserEntity currentUser = getCurrentUser();
        if (!income.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ExceptionMessage.INCOME_NOT_OWNED);
        }
    }
}
