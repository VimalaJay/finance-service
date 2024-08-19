package com.finance.fundtransactionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.fundtransactionservice.entity.FundTransaction;

public interface FundTransactionRepo extends JpaRepository<FundTransaction, Long> {
	
	List<FundTransaction> findByAccountId(String accountId);

}
