package com.inghub.project.domain;

import com.inghub.project.domain.customer.Customer;
import com.inghub.project.domain.exception.InsufficientCreditLimitException;
import com.inghub.project.domain.exception.InvalidLoanParametersException;
import com.inghub.project.domain.loan.Loan;
import com.inghub.project.domain.loan.LoanDomainService;
import com.inghub.project.domain.loan.LoanValidator;
import com.inghub.project.domain.loaninstallment.LoanInstallment;
import com.inghub.project.userinterface.dto.PaymentResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanDomainServiceTest {

    private LoanDomainService loanDomainService;

    @BeforeEach
    void setUp() {
        LoanValidator loanValidator = new LoanValidator();
        loanDomainService = new LoanDomainService(loanValidator);
    }

    @Test
    public void testCreateLoan_success() {
        Customer customer = new Customer("John", "Doe", BigDecimal.valueOf(10000), BigDecimal.valueOf(0));
        BigDecimal amount = BigDecimal.valueOf(5000);
        BigDecimal interestRate = BigDecimal.valueOf(0.1);
        int numberOfInstallments = 12;

        Loan loan = loanDomainService.createLoan(customer, amount, interestRate, numberOfInstallments);

        assertNotNull(loan);
        assertEquals((interestRate.add(BigDecimal.valueOf(1)).multiply(amount)), loan.getLoanAmount());
        assertEquals(numberOfInstallments, loan.getNumberOfInstallments());
        assertFalse(loan.getIsPaid());
        assertEquals(12, loan.getInstallments().size());
    }

    @Test
    public void testProcessPayment_multipleInstallmentsPaid() {
        Loan loan = createLoanWithInstallments();
        BigDecimal paymentAmount = BigDecimal.valueOf(300);

        PaymentResult result = loanDomainService.processPayment(loan, paymentAmount);

        assertEquals(3, result.getInstallmentsPaid());
        assertEquals(paymentAmount, result.getTotalAmountPaid());
        assertFalse(result.isLoanFullyPaid());
    }

    @Test
    public void testProcessPayment_singleInstallmentPaid() {
        Loan loan = createLoanWithInstallments();
        BigDecimal paymentAmount = BigDecimal.valueOf(100);

        PaymentResult result = loanDomainService.processPayment(loan, paymentAmount);

        assertEquals(1, result.getInstallmentsPaid());
        assertEquals(paymentAmount, result.getTotalAmountPaid());
        assertFalse(result.isLoanFullyPaid());
    }

    @Test
    public void testCreateLoan_insufficientCreditLimit() {
        Customer customer = new Customer("John", "Doe", BigDecimal.valueOf(4000), BigDecimal.valueOf(0));
        BigDecimal amount = BigDecimal.valueOf(5000);
        BigDecimal interestRate = BigDecimal.valueOf(0.1);
        int numberOfInstallments = 12;

        Exception exception = assertThrows(InsufficientCreditLimitException.class, () -> {
            loanDomainService.createLoan(customer, amount, interestRate, numberOfInstallments);
        });

        assertEquals("Customer does not have enough credit limit for this loan.", exception.getMessage());
    }

    @Test
    public void testCreateLoan_invalidNumberOfInstallments() {
        Customer customer = new Customer("Jane", "Smith", BigDecimal.valueOf(10000), BigDecimal.valueOf(0));
        BigDecimal amount = BigDecimal.valueOf(5000);
        BigDecimal interestRate = BigDecimal.valueOf(0.1);
        int numberOfInstallments = 8;

        Exception exception = assertThrows(InvalidLoanParametersException.class, () -> {
            loanDomainService.createLoan(customer, amount, interestRate, numberOfInstallments);
        });

        assertEquals("Number of installments not a valid number.", exception.getMessage());
    }

    private Loan createLoanWithInstallments() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(1200));
        loan.setNumberOfInstallments(12);

        List<LoanInstallment> installments = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LoanInstallment installment = new LoanInstallment();
            installment.setAmount(BigDecimal.valueOf(100));
            installment.setDueDate(LocalDate.now().plusMonths(i + 1));
            installment.setIsPaid(false);
            installments.add(installment);
        }
        loan.setInstallments(installments);
        return loan;
    }
}

