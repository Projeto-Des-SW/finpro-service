package com.ufape.finproservice.dto.response;

import java.util.List;

import com.ufape.finproservice.dto.QuestionAnswerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuestionnaireResponseDTO {
    @NotEmpty
    @Valid
    private List<QuestionAnswerDTO> answers;
}
