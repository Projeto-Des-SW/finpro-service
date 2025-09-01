package com.ufape.finproservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.ufape.finproservice.dto.ExpenseCategoryDTO;
import com.ufape.finproservice.dto.response.ExpenseCategoryResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.ExpenseCategoryMapper;
import com.ufape.finproservice.model.ExpenseCategory;
import com.ufape.finproservice.repository.ExpenseCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryResponseDTO createExpenseCategory(ExpenseCategoryDTO expenseCategoryDTO) {
        if (expenseCategoryRepository.findByName(expenseCategoryDTO.getName()).isPresent()) {
            throw new CustomException(ExceptionMessage.EXPENSE_CATEGORY_ALREADY_EXISTS);
        }

        ExpenseCategory expenseCategory = ExpenseCategoryMapper.toEntity(expenseCategoryDTO);
        ExpenseCategory savedExpenseCategory = expenseCategoryRepository.save(expenseCategory);
        return ExpenseCategoryMapper.toExpenseCategoryResponseDTO(savedExpenseCategory);
    }

    public ExpenseCategoryResponseDTO findExpenseCategoryById(Long id) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));
        return ExpenseCategoryMapper.toExpenseCategoryResponseDTO(expenseCategory);
    }

    public List<ExpenseCategoryResponseDTO> findAllExpenseCategories() {
        return expenseCategoryRepository.findAll().stream()
                .map(ExpenseCategoryMapper::toExpenseCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    public ExpenseCategoryResponseDTO updateExpenseCategory(Long id, ExpenseCategoryDTO expenseCategoryDTO) {
        ExpenseCategory existingExpenseCategory = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));

        if (!existingExpenseCategory.getName().equals(expenseCategoryDTO.getName()) &&
            expenseCategoryRepository.findByName(expenseCategoryDTO.getName()).isPresent()) {
            throw new CustomException(ExceptionMessage.EXPENSE_CATEGORY_ALREADY_EXISTS);
        }

        existingExpenseCategory.setName(expenseCategoryDTO.getName());
        ExpenseCategory updatedExpenseCategory = expenseCategoryRepository.save(existingExpenseCategory);
        return ExpenseCategoryMapper.toExpenseCategoryResponseDTO(updatedExpenseCategory);
    }

    public void deleteExpenseCategory(Long id) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));
        expenseCategoryRepository.delete(expenseCategory);
    }
}
