package com.ufape.finproservice.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvestorProfileRequestDTO {
    @NotEmpty
    @Valid
    private List<QuestionAnswerDTO> answers;
}
