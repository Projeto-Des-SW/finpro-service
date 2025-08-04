package com.ufape.finproservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "income_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeCategoryId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "incomeCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Income> incomes = new ArrayList<>();

}
