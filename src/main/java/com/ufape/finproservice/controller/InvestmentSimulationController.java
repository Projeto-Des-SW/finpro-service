package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.InvestmentSimulationDTO;
import com.ufape.finproservice.dto.response.InvestmentSimulationResponseDTO;
import com.ufape.finproservice.service.InvestmentSimulationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investment")
@AllArgsConstructor
public class InvestmentSimulationController {

    private final InvestmentSimulationService simulationService;

    @PostMapping("/simulate")
    public InvestmentSimulationResponseDTO simulate(@RequestBody InvestmentSimulationDTO dto) {
        return simulationService.simulate(dto);
    }
}
