package com.finance.fundtransactionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.fundtransactionservice.model.FundTransactionDTO;
import com.finance.fundtransactionservice.service.FundTransactionService;
import com.finance.fundtransactionservice.util.Response;

import jakarta.validation.constraints.NotBlank;


@RestController
@Validated
@RequestMapping("/api/v1/transactions")
public class FundTransactionController {
	
	@Autowired
	private FundTransactionService transactionService;
	
	@PostMapping("/add")
	public ResponseEntity<Response> createTransaction(@RequestBody FundTransactionDTO transactionDTO) {
		return new ResponseEntity<Response>(transactionService.createTransaction(transactionDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("/transaction/{accountId}")
	public ResponseEntity<List<FundTransactionDTO>> getTransactions(@NotBlank @PathVariable String accountId) {
		return new ResponseEntity<>(transactionService.getTransactionsByAccountId(accountId), HttpStatus.OK);
	}
	
}
