package com.finance.accountservice.dto;

import lombok.Data;

@Data
public class FundTransactionDTO {
	
	private Long transactionId;
	private String transactionStatus;
	private String transactionType;
	private String accountId;
	private Double amount;

}
