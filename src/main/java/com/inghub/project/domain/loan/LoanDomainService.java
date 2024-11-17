package com.inghub.project.domain.loan;

import com.inghub.project.domain.customer.Customer;
import com.inghub.project.domain.loaninstallment.LoanInstallment;
import com.inghub.project.userinterface.dto.PaymentResult;
import com.inghub.project.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanDomainService {

    private final LoanValidator loanValidator;

    public LoanDomainService(LoanValidator loanValidator) {
        this.loanValidator = loanValidator;
    }

    public Loan createLoan(Customer customer, BigDecimal amount, BigDecimal interestRate, int numberOfInstallments) {
        loanValidator.validateLoanParameters(interestRate, numberOfInstallments);
        loanValidator.validateCreditLimit(customer, amount);

        BigDecimal totalLoanAmount = calculateTotalLoanAmount(amount, interestRate);

        Loan loan = new Loan(customer, totalLoanAmount, numberOfInstallments, interestRate);

        List<LoanInstallment> installments = createInstallments(loan, totalLoanAmount, numberOfInstallments);

        loan.setInstallments(installments);

        customer.adjustCreditLimit(amount);

        return loan;
    }
    private BigDecimal calculateTotalLoanAmount(BigDecimal amount, BigDecimal interestRate) {
        return amount.multiply(BigDecimal.ONE.add(interestRate));
    }

    private List<LoanInstallment> createInstallments(Loan loan, BigDecimal totalLoanAmount, int numberOfInstallments) {
        BigDecimal installmentAmount = totalLoanAmount.divide(
                BigDecimal.valueOf(numberOfInstallments),
                2,
                BigDecimal.ROUND_HALF_UP
        );

        List<LoanInstallment> installments = new ArrayList<>();
        LocalDate dueDate = DateUtils.getFirstDayOfNextMonth(LocalDate.now());

        for (int i = 0; i < numberOfInstallments; i++) {
            LoanInstallment installment = new LoanInstallment(
                    loan,
                    installmentAmount,
                    dueDate
            );
            installments.add(installment);
            dueDate = dueDate.plusMonths(1);
        }

        return installments;
    }

    public PaymentResult processPayment(Loan loan, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }

        BigDecimal remainingAmount = amount;
        int installmentsPaid = 0;
        BigDecimal totalAmountPaid = BigDecimal.ZERO;

        LocalDate maxPayableDate = LocalDate.now().plusMonths(3);

        for (LoanInstallment installment : loan.getInstallments()) {
            if (installment.getIsPaid()) {
                continue;
            }

            if (installment.getDueDate().isAfter(maxPayableDate)) {
                break;
            }

            if (remainingAmount.compareTo(installment.getAmount()) >= 0) {
                installment.pay(installment.getAmount(), LocalDate.now());
                remainingAmount = remainingAmount.subtract(installment.getAmount());
                totalAmountPaid = totalAmountPaid.add(installment.getAmount());
                installmentsPaid++;
            } else {
                break;
            }
        }

        boolean isLoanFullyPaid = loan.getInstallments().stream().allMatch(LoanInstallment::getIsPaid);
        if (isLoanFullyPaid) {
            loan.markAsPaid();
        }

        return new PaymentResult(installmentsPaid, totalAmountPaid, isLoanFullyPaid);
    }
}
