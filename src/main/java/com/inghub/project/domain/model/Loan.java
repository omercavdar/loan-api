package com.inghub.project.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Column(name = "number_of_installments")
    private Integer numberOfInstallments;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanInstallment> installments;

    public Loan(Customer customer, BigDecimal loanAmount, Integer numberOfInstallments) {
        this.customer = customer;
        this.loanAmount = loanAmount;
        this.numberOfInstallments = numberOfInstallments;
        this.createDate =  LocalDate.now();
    }
}
