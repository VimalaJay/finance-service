package com.finance.fundtransactionservice.service;

import java.util.List;

import com.finance.fundtransactionservice.model.FundTransactionDTO;
import com.finance.fundtransactionservice.util.Response;

public interface FundTransactionService {
	
	public Response createTransaction(FundTransactionDTO transactionDTO);
	
	public List<FundTransactionDTO> getTransactionsByAccountId(String accountId);

}
