package com.inghub.project.domain.customer;

import com.inghub.project.domain.loan.Loan;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "used_credit_limit")
    private BigDecimal usedCreditLimit = BigDecimal.ZERO;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

    public Customer(String name, String surname, BigDecimal creditLimit, BigDecimal usedCreditLimit) {
        this.name = name;
        this.surname = surname;
        this.creditLimit = creditLimit;
        this.usedCreditLimit = usedCreditLimit;
    }

    public void adjustCreditLimit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        BigDecimal newUsedCreditLimit = this.usedCreditLimit.add(amount);
        if (newUsedCreditLimit.compareTo(this.creditLimit) > 0) {
            throw new IllegalStateException("Exceeds available credit limit");
        }
        this.usedCreditLimit = newUsedCreditLimit;
        this.creditLimit = this.creditLimit.subtract(amount);
    }

    public BigDecimal getAvailableCreditLimit() {
        return creditLimit.subtract(usedCreditLimit);
    }
}
