package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.ExpenseMapper;
import com.ufape.finproservice.model.ExpenseEntity;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.ExpenseRepository;
import com.ufape.finproservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/*import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;*/
@AllArgsConstructor
@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private UserRepository userRepository;

    @Transactional
    public ExpenseResponseDTO registerExpense(ExpenseDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.USER_NOT_FOUND));

        ExpenseEntity expense = ExpenseMapper.toEntity(dto, user);
        ExpenseEntity saved = expenseRepository.save(expense);
        return ExpenseMapper.toExpenseResponseDTO(saved);
    }

/*    public List<ExpenseResponseDTO> listByPeriod(Long userId, LocalDate start, LocalDate end) {
        return expenseRepository.findByUserIdAndDateBetween(userId, start, end).stream()
                .map(ExpenseMapper::toExpenseResponseDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateTotalByCategory(Long userId, String category) {
        return expenseRepository.findByUserIdAndCategory(userId, category).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<ExpenseResponseDTO> listByUser(Long userId) {
        return expenseRepository.findByUserId(userId).stream()
                .map(ExpenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
 */
}
