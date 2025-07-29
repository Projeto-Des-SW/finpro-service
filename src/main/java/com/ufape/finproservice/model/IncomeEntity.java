package com.ufape.finproservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "incomes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID incomeId;

    @Column(nullable = false)
    private LocalDate date;

    /*@ManyToOne
    @JoinColumn(name = "income_category_id")
    private IncomeCategoryEntity category;*/

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentOrigin;

    @Column(nullable = false)
    private String balanceSource;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // Construtores
    /*public IncomeEntity(String uuid, LocalDate date, IncomeCategoryEntity category, BigDecimal amount, 
                      String paymentOrigin, String balanceSource, String ovbservation, UserEntity user) {
        this.uuid = uuid;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.paymentOrigin = paymentOrigin;
        this.balanceSource = balanceSource;
        this.observation = observation;
        this.user = user;
    }*/

    // Getters e Setters
    public UUID getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(UUID incomeId) {
        this.incomeId = incomeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /*public IncomeCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(IncomeCategoryEntity category) {
        this.category = category;
    }*/

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentOrigin() {
        return paymentOrigin;
    }

    public void setPaymentOrigin(String paymentOrigin) {
        this.paymentOrigin = paymentOrigin;
    }

    public String getBalanceSource() {
        return balanceSource;
    }

    public void setBalanceSource(String balanceSource) {
        this.balanceSource = balanceSource;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}