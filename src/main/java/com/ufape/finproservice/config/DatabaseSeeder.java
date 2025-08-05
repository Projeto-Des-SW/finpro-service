package com.ufape.finproservice.config;

import com.ufape.finproservice.model.ExpenseCategory;
import com.ufape.finproservice.model.IncomeCategory;
import com.ufape.finproservice.repository.ExpenseCategoryRepository;
import com.ufape.finproservice.repository.IncomeCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    public DatabaseSeeder(ExpenseCategoryRepository expenseCategoryRepository,
                          IncomeCategoryRepository incomeCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    public void run(String... args) {
        seedExpenseCategories();
        seedIncomeCategories();
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
}
