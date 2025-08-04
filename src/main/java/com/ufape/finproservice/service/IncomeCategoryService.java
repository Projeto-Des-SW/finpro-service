package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.IncomeCategoryDTO;
import com.ufape.finproservice.dto.IncomeCategoryResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.IncomeCategoryMapper;
import com.ufape.finproservice.model.IncomeCategory;
import com.ufape.finproservice.repository.IncomeCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class IncomeCategoryService {

    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategoryResponseDTO createIncomeCategory(IncomeCategoryDTO incomeCategoryDTO) {
        if(incomeCategoryRepository.findByName(incomeCategoryDTO.getName()).isPresent()) {
            throw new CustomException(ExceptionMessage.INCOME_CATEGORY_ALREADY_EXISTS);
        }


    IncomeCategory incomeCategory = IncomeCategoryMapper.toEntity(incomeCategoryDTO);
    IncomeCategory savedIncomeCategory = incomeCategoryRepository.save(incomeCategory);
    return IncomeCategoryMapper.toIncomeCategoryResponseDTO(savedIncomeCategory);
    }

    public IncomeCategoryResponseDTO findIncomeCategoryById(Long id){
        IncomeCategory incomeCategory = incomeCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_CATEGORY_NOT_FOUND));

        return IncomeCategoryMapper.toIncomeCategoryResponseDTO(incomeCategory);
    }

    public List<IncomeCategoryResponseDTO> findAllIncomeCategories() {
        return incomeCategoryRepository.findAll().stream()
                .map(IncomeCategoryMapper::toIncomeCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    public IncomeCategoryResponseDTO updateIncomeCategory(Long id, IncomeCategoryDTO incomeCategoryDTO){
        IncomeCategory existingIncomeCategory = incomeCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_CATEGORY_NOT_FOUND));

        if (!existingIncomeCategory.getName().equals(incomeCategoryDTO.getName()) &&
            incomeCategoryRepository.findByName(incomeCategoryDTO.getName()).isPresent()) {
            throw new CustomException(ExceptionMessage.INCOME_CATEGORY_ALREADY_EXISTS);
        }

        existingIncomeCategory.setName(incomeCategoryDTO.getName());

        IncomeCategory updatedIncomeCategory = incomeCategoryRepository.save(existingIncomeCategory);
        return IncomeCategoryMapper.toIncomeCategoryResponseDTO(updatedIncomeCategory);
    }

    public void deleteIncomeCategory(Long id) {
        IncomeCategory incomeCategory = incomeCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.INCOME_CATEGORY_NOT_FOUND));

        incomeCategoryRepository.delete(incomeCategory);
    }
}
