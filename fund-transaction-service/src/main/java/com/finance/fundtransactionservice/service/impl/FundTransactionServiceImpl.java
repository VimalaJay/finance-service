package com.finance.fundtransactionservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.fundtransactionservice.entity.FundTransaction;
import com.finance.fundtransactionservice.exceptions.ServiceException;
import com.finance.fundtransactionservice.model.FundTransactionDTO;
import com.finance.fundtransactionservice.repository.FundTransactionRepo;
import com.finance.fundtransactionservice.service.FundTransactionService;
import com.finance.fundtransactionservice.util.Constants;
import com.finance.fundtransactionservice.util.Response;
import com.finance.fundtransactionservice.util.TransactionType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FundTransactionServiceImpl implements FundTransactionService {

	@Autowired
	private FundTransactionRepo transactionRepo;

	@Transactional
	public Response createTransaction(FundTransactionDTO transactionDTO) {
		FundTransaction transaction = new FundTransaction();
		transaction.setTransactionId(generateTransactionId());
		transaction.setAccountId(transactionDTO.getAccountId());
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setTransactionType(TransactionType.CREDIT.name());
		transaction.setTransactionStatus(Constants.status);
		
		try {
			transactionRepo.save(transaction);
		} catch (Exception e) {
			log.error("Transaction failed for account {}", transactionDTO.getAccountId());
			throw new ServiceException("Transaction failed!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new Response("Transaction created successfully!", HttpStatus.CREATED);
	}

	public List<FundTransactionDTO> getTransactionsByAccountId(String accountId) {
		List<FundTransaction> fundTransactions = transactionRepo.findByAccountId(accountId);
		
		//acount id validation required
		
		if (fundTransactions.isEmpty()) {
			throw new ServiceException("No transactions created for this account!", HttpStatus.NO_CONTENT);
		}
		
		List<FundTransactionDTO> fundTransactionDtos = new ArrayList<>();
		for (FundTransaction transaction : fundTransactions) {
			FundTransactionDTO transactionDTO = new FundTransactionDTO();
			BeanUtils.copyProperties(transaction, transactionDTO);
			fundTransactionDtos.add(transactionDTO);
		}
		
		return fundTransactionDtos;
	}
	
	private Long generateTransactionId() {
		Random random = new Random();
		return random.nextLong(1000000);
	}
	
}
