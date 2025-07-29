package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.IncomeDTO;
import com.ufape.finproservice.dto.IncomeResponseDTO;
import com.ufape.finproservice.model.IncomeEntity;
import com.ufape.finproservice.model.UserEntity;

public class IncomeMapper {

    public static IncomeEntity toEntity(IncomeDTO dto, UserEntity user /*, IncomeCategoryEntity category*/) {
        if (dto == null) return null;
        
        return IncomeEntity.builder()
                .date(dto.getDate())
                .amount(dto.getAmount())
                .paymentOrigin(dto.getPaymentOrigin())
                .balanceSource(dto.getBalanceSource())
                .observation(dto.getObservation())
                .user(user)
                //.category(category)
                .build();
    }

    public static IncomeResponseDTO toIncomeResponseDTO(IncomeEntity income) {
        if (income == null) return null;
        
        return IncomeResponseDTO.builder()
                .incomeId(income.getIncomeId())
                .date(income.getDate())
                .amount(income.getAmount())
                .paymentOrigin(income.getPaymentOrigin())
                .balanceSource(income.getBalanceSource())
                .observation(income.getObservation())
                .userId(income.getUser().getId())
                /*.category(income.getCategory() != null ? 
                    IncomeCategoryMapper.toIncomeCategoryResponseDTO(income.getCategory()) : null)*/
                .build();
    }
}
