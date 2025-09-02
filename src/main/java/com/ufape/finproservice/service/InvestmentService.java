package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.InvestmentRecommendationDTO;
import com.ufape.finproservice.dto.InvestmentSimulationDTO;
import com.ufape.finproservice.dto.response.InvestmentSimulationResponseDTO;
import com.ufape.finproservice.enumeration.RiskProfile;
import com.ufape.finproservice.model.InvestorProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InvestmentService {

    private static final String TAXA_API_URL = "https://brasilapi.com.br/api/taxas/v1";
    private final InvestorProfileService investorProfileService;

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

    public InvestmentRecommendationDTO recommend() {
        InvestorProfile profile = investorProfileService.getCurrentUserProfile();
        RiskProfile risk = profile.getRiskProfile();
        return InvestmentRecommendationDTO.builder()
                .riskProfile(risk)
                .recommendations(getRecommendationsByRisk(risk))
                .build();
    }

    private List<String> getRecommendationsByRisk(RiskProfile riskProfile) {
        return switch (riskProfile) {
            case CONSERVATIVE -> List.of(
                    "Tesouro Selic",
                    "CDB - liquidez diária",
                    "LCI/LCA",
                    "Fundos DI",
                    "Poupança (último recurso)"
            );
            case MODERATE -> List.of(
                    "Tesouro IPCA+",
                    "CDBs de bancos médios",
                    "Fundos Multimercado",
                    "Fundos Imobiliários",
                    "ETFs"
            );
            case AGGRESSIVE -> List.of(
                    "Ações individuais (ex: PETR4, VALE3)",
                    "ETFs internacionais (ex: NASD11, HASH11)",
                    "Criptomoedas",
                    "Debêntures, CRIs, CRAs",
                    "Investimentos alternativos (startups)"
            );
        };
    }
}
