package com.inghub.project.domain.loan;

import com.inghub.project.domain.customer.Customer;
import com.inghub.project.domain.exception.InsufficientCreditLimitException;
import com.inghub.project.domain.exception.InvalidLoanParametersException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class LoanValidator {

    private static final BigDecimal MIN_INTEREST_RATE = BigDecimal.valueOf(0.1);
    private static final BigDecimal MAX_INTEREST_RATE = BigDecimal.valueOf(0.5);
    private static final List<Integer> VALID_INSTALLMENTS = List.of(6, 9, 12, 24);

    public void validateLoanParameters(BigDecimal interestRate, int numberOfInstallments) {
        validateInterestRate(interestRate);
        validateNumberOfInstallments(numberOfInstallments);
    }

    private void validateInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(MIN_INTEREST_RATE) < 0 || interestRate.compareTo(MAX_INTEREST_RATE) > 0) {
            throw new InvalidLoanParametersException("Interest rate must be between " + MIN_INTEREST_RATE + " and " + MAX_INTEREST_RATE + " inclusive.");
        }
    }

    private void validateNumberOfInstallments(int numberOfInstallments) {
        if (!VALID_INSTALLMENTS.contains(numberOfInstallments)) {
            throw new InvalidLoanParametersException("Number of installments not a valid number.");
        }
    }

    public void validateCreditLimit(Customer customer, BigDecimal totalLoanAmount) {
        BigDecimal availableCredit = customer.getAvailableCreditLimit();
        if (totalLoanAmount.compareTo(availableCredit) > 0) {
            throw new InsufficientCreditLimitException("Customer does not have enough credit limit for this loan.");
        }
    }
}
