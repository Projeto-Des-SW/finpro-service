package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.InvestorProfileRequestDTO;
import com.ufape.finproservice.dto.response.InvestorProfileResponseDTO;
import com.ufape.finproservice.dto.ProfileCalculationResultDTO;
import com.ufape.finproservice.dto.response.QuestionnaireResponseDTO;
import com.ufape.finproservice.service.InvestorProfileService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/investor-profile")
@AllArgsConstructor
public class InvestorProfileController {
    
    private final InvestorProfileService investorProfileService;
    
    @PostMapping("/calculate")
    public ResponseEntity<ProfileCalculationResultDTO> calculateProfile(
            @Valid @RequestBody QuestionnaireResponseDTO questionnaireDTO) {
        ProfileCalculationResultDTO result = investorProfileService.calculateProfile(questionnaireDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<InvestorProfileResponseDTO> saveProfile(
            @Valid @RequestBody InvestorProfileRequestDTO requestDTO) {
        InvestorProfileResponseDTO response = investorProfileService.saveProfile(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/current")
    public ResponseEntity<InvestorProfileResponseDTO> getProfile() {
        InvestorProfileResponseDTO response = investorProfileService.getCurrentUserProfileDTO();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping
    public ResponseEntity<InvestorProfileResponseDTO> updateProfile(
            @Valid @RequestBody InvestorProfileRequestDTO requestDTO) {
        InvestorProfileResponseDTO response = investorProfileService.saveProfile(requestDTO);
        return ResponseEntity.ok(response);
    }
}
