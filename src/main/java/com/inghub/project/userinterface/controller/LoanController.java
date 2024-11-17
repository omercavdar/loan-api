package com.inghub.project.userinterface.controller;

import com.inghub.project.application.loan.LoanApplicationService;
import com.inghub.project.domain.exception.InvalidLoanParametersException;
import com.inghub.project.userinterface.dto.CreateLoanRequest;
import com.inghub.project.userinterface.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanApplicationService loanApplicationService;

    public LoanController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    public ResponseEntity<String> createLoan(@RequestBody CreateLoanRequest request) {
        try {
            LoanDto loanDto = loanApplicationService.createLoan(request);
            return ResponseEntity.ok("Successfully created loan with id: " + loanDto.getId());
        } catch (InvalidLoanParametersException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
