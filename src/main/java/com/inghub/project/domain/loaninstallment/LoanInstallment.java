package com.inghub.project.domain.loaninstallment;

import com.inghub.project.domain.loan.Loan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loan_installments")
public class LoanInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public LoanInstallment(Loan loan, BigDecimal amount, LocalDate dueDate) {
        this.loan = loan;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paymentDate = null;
    }
    public void pay(BigDecimal payment, LocalDate paymentDate) {
        if (payment == null || payment.compareTo(this.amount) != 0) {
            throw new IllegalArgumentException("Installment must be paid in full");
        }
        this.paidAmount = payment;
        this.paymentDate = paymentDate;
        this.isPaid = true;
    }

}
