package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.InvestmentSimulationDTO;
import com.ufape.finproservice.dto.response.InvestmentSimulationResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class InvestmentSimulationService {

    private static final String TAXA_API_URL = "https://brasilapi.com.br/api/taxas/v1";

    public InvestmentSimulationResponseDTO simulate(InvestmentSimulationDTO dto) {
        RestTemplate restTemplate = new RestTemplate();
        var taxas = restTemplate.getForObject(TAXA_API_URL, Map[].class);

        double taxaAnualBase = 0;

        for (Map taxa : taxas) {
            if (taxa.get("nome").toString().equalsIgnoreCase(dto.investmentType().name())) {
                taxaAnualBase = Double.parseDouble(taxa.get("valor").toString());
                break;
            }
        }

        if (taxaAnualBase == 0) {
            throw new RuntimeException("Indexador inválido ou não encontrado");
        }

        double taxaAnualEfetiva = taxaAnualBase * (dto.percentInvestmentType() / 100.0);
        double taxaMensal = Math.pow(1 + taxaAnualEfetiva / 100, 1.0 / 12) - 1;

        double montante = dto.startValue();
        for (int i = 1; i <= dto.months(); i++) {
            montante *= (1 + taxaMensal);
            montante += dto.monthlyValue();
        }

        double totalInvestido = dto.startValue() + dto.monthlyValue() * dto.months();
        double rendimento = montante - totalInvestido;

        return InvestmentSimulationResponseDTO.builder()
                .valorFinal(round(montante))
                .totalInvestido(round(totalInvestido))
                .rendimentoTotal(round(rendimento))
                .taxaEfetivaAnual(round(taxaAnualEfetiva))
                .taxaEfetivaMensal(round(taxaMensal * 100))
                .build();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
