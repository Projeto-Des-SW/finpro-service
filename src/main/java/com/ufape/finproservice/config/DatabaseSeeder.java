package com.ufape.finproservice.config;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.enumeration.*;
import com.ufape.finproservice.model.*;
import com.ufape.finproservice.repository.*;
import com.ufape.finproservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final UserService userService;
    private final EducationalContentRepository educationalContentRepository;
    private final InvestorProfileRepository investorProfileRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final PiggyBankRepository piggyBankRepository;
    private final String PASSWORD = "123456789";
    private final String USER_EMAIL = "user@finpro.com";
    private final String ADMIN_EMAIL = "admin@finpro.com";

    @Override
    @Transactional
    public void run(String... args) {
        seedExpenseCategories();
        seedIncomeCategories();
        seedAdminUser();
        seedAndPopulateCommonUser();
    }

    private void seedExpenseCategories() {
        List<String> defaultExpenses = List.of("Alimentação", "Transporte", "Moradia", "Lazer", "Saúde");
        defaultExpenses.forEach(name ->
                expenseCategoryRepository.findByName(name)
                        .orElseGet(() -> expenseCategoryRepository.save(
                                ExpenseCategory.builder().name(name).build()
                        ))
        );
    }

    private void seedIncomeCategories() {
        List<String> defaultIncomes = List.of("Salário", "Investimento", "Bolsa");
        defaultIncomes.forEach(name ->
                incomeCategoryRepository.findByName(name)
                        .orElseGet(() -> incomeCategoryRepository.save(
                                IncomeCategory.builder().name(name).build()
                        ))
        );
    }

    private void seedAdminUser() {
        userService.findByEmail(ADMIN_EMAIL).ifPresentOrElse(
                user -> log.info("User {} already existis", ADMIN_EMAIL),
                () -> {
                    userService.save(
                            UserDTO.builder()
                                    .email(ADMIN_EMAIL)
                                    .password(PASSWORD)
                                    .role(Role.ADMIN)
                                    .build()
                    );
                    seedEducationalContent();
                }
        );
    }

    private void seedEducationalContent() {
        List<EducationalContent> contents = List.of(
                EducationalContent.builder()
                        .title("Orçamento Pessoal: Primeiros Passos")
                        .description("Aprenda a organizar seu orçamento e controlar gastos com dicas práticas.")
                        .videoUrl("https://www.youtube.com/watch?v=123abc")
                        .fileUrl(null)
                        .category("Educação Financeira")
                        .build(),

                EducationalContent.builder()
                        .title("Como Funciona o Tesouro Direto")
                        .description("Entenda como investir no Tesouro Direto, tipos de títulos e prazos.")
                        .videoUrl("https://www.youtube.com/watch?v=tesouro456")
                        .fileUrl(null)
                        .category("Investimentos")
                        .build(),

                EducationalContent.builder()
                        .title("CDI, IPCA e SELIC: Diferenças e Aplicações")
                        .description("Guia simplificado sobre os principais indexadores econômicos.")
                        .videoUrl(null)
                        .fileUrl("https://meusite.com/files/indexadores.pdf")
                        .category("Economia")
                        .build(),

                EducationalContent.builder()
                        .title("Como Criar Metas Financeiras Inteligentes")
                        .description("Aprenda a criar metas de curto, médio e longo prazo com planejamento eficaz.")
                        .videoUrl("https://www.youtube.com/watch?v=metas789")
                        .fileUrl(null)
                        .category("Planejamento Pessoal")
                        .build()
        );

        educationalContentRepository.saveAll(contents);
        log.info("EducationalContent populated - {} registers.", contents.size());
    }

    private void seedAndPopulateCommonUser() {
        userService.findByEmail(USER_EMAIL).ifPresentOrElse(
                user -> log.info("User {} already existis", USER_EMAIL),
                () -> {
                    UserEntity newUser = userService.save(
                            UserDTO.builder()
                                    .email(USER_EMAIL)
                                    .password(PASSWORD)
                                    .role(Role.USER)
                                    .build()
                    );
                    seedInvestorProfile(newUser);
                    populateExpenses(newUser);
                    populateIncomes(newUser);
                    populatePiggyBank(newUser);
                }
        );
    }

    private void seedInvestorProfile(UserEntity user) {
        investorProfileRepository.save(InvestorProfile.builder()
                .riskProfile(RiskProfile.AGGRESSIVE)
                .score(10)
                .riskTolerance(RiskTolerance.HIGH)
                .investmentTerm(InvestmentTerm.LONG_TERM)
                .knowledgeLevel(KnowledgeLevel.ADVANCED)
                .user(user)
                .build()
        );
    }

    private void populateExpenses(UserEntity user) {
        List<ExpenseCategory> categories = expenseCategoryRepository.findAll();
        List<Expense> expenses = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ExpenseCategory category = categories.get(i % categories.size());

            expenses.add(Expense.builder()
                    .date(LocalDate.now().withDayOfMonth(5 + i))
                    .expenseCategory(category)
                    .amount(BigDecimal.valueOf(50 + i * 10))
                    .paymentDestination("Despesa Atual " + (i + 1))
                    .balanceSource("Conta Corrente")
                    .observation("Despesa deste mês nº " + (i + 1))
                    .user(user)
                    .build()
            );

            expenses.add(Expense.builder()
                    .date(LocalDate.now().minusMonths(1).withDayOfMonth(5 + i))
                    .expenseCategory(category)
                    .amount(BigDecimal.valueOf(60 + i * 12))
                    .paymentDestination("Despesa Anterior " + (i + 1))
                    .balanceSource("Cartão de Crédito")
                    .observation("Despesa do mês anterior nº " + (i + 1))
                    .user(user)
                    .build()
            );
        }

        expenseRepository.saveAll(expenses);
        log.info("Expenses populated - {} registros", expenses.size());
    }

    private void populateIncomes(UserEntity user) {
        List<IncomeCategory> categories = incomeCategoryRepository.findAll();
        List<Income> incomes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            IncomeCategory category = categories.get(i % categories.size());

            incomes.add(Income.builder()
                    .date(LocalDate.now().withDayOfMonth(3 + i))
                    .incomeCategory(category)
                    .amount(BigDecimal.valueOf(200 + i * 25))
                    .paymentOrigin("Receita Atual " + (i + 1))
                    .balanceSource("Conta Corrente")
                    .observation("Receita deste mês nº " + (i + 1))
                    .user(user)
                    .build()
            );

            incomes.add(Income.builder()
                    .date(LocalDate.now().minusMonths(1).withDayOfMonth(3 + i))
                    .incomeCategory(category)
                    .amount(BigDecimal.valueOf(180 + i * 22))
                    .paymentOrigin("Receita Anterior " + (i + 1))
                    .balanceSource("Conta Poupança")
                    .observation("Receita do mês anterior nº " + (i + 1))
                    .user(user)
                    .build()
            );
        }

        incomeRepository.saveAll(incomes);
        log.info("Incomes populated - {} registros", incomes.size());
    }

    private void populatePiggyBank(UserEntity user) {
        List<PiggyBank> piggyBanks = List.of(
                PiggyBank.builder()
                        .name("Viagem para o Nordeste")
                        .monthlyDeposit(BigDecimal.valueOf(300))
                        .savingsGoal(BigDecimal.valueOf(5000))
                        .currentAmount(BigDecimal.valueOf(900))
                        .targetDate(LocalDate.now().plusMonths(10))
                        .status(Status.ON_TRACK)
                        .depositDay(10)
                        .lastDepositDate(LocalDate.now().minusDays(5))
                        .user(user)
                        .build(),

                PiggyBank.builder()
                        .name("Comprar um Notebook")
                        .monthlyDeposit(BigDecimal.valueOf(250))
                        .savingsGoal(BigDecimal.valueOf(3500))
                        .currentAmount(BigDecimal.valueOf(750))
                        .targetDate(LocalDate.now().plusMonths(12))
                        .status(Status.BEHIND)
                        .depositDay(5)
                        .lastDepositDate(LocalDate.now().minusDays(2))
                        .user(user)
                        .build(),

                PiggyBank.builder()
                        .name("Reserva de Emergência")
                        .monthlyDeposit(BigDecimal.valueOf(400))
                        .savingsGoal(BigDecimal.valueOf(10000))
                        .currentAmount(BigDecimal.valueOf(4000))
                        .targetDate(LocalDate.now().plusMonths(24))
                        .status(Status.COMPLETED)
                        .depositDay(15)
                        .lastDepositDate(LocalDate.now().minusDays(10))
                        .user(user)
                        .build()
        );

        piggyBankRepository.saveAll(piggyBanks);
        log.info("PiggyBanks populated - {} registers", piggyBanks.size());
    }

}
