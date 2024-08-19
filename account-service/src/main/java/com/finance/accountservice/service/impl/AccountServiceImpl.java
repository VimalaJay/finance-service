package com.finance.accountservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.finance.accountservice.Entity.Account;
import com.finance.accountservice.Entity.Customer;
import com.finance.accountservice.dto.AccountDTO;
import com.finance.accountservice.dto.FundTransactionDTO;
import com.finance.accountservice.exceptions.ServiceException;
import com.finance.accountservice.external.service.TransactionRestService;
import com.finance.accountservice.repository.AccountRepo;
import com.finance.accountservice.repository.CustomerRepo;
import com.finance.accountservice.service.AccountService;
import com.finance.accountservice.util.AccountType;
import com.finance.accountservice.util.Response;
import com.finance.accountservice.util.ServiceUtil;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	ServiceUtil util;
	
	@Autowired
	TransactionRestService transactionService;
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public Response createAccount(String customerId, Double initialCredit) throws Exception {
		
		Optional<Customer> customerEntity = customerRepo.findByCustomerId(customerId);
		
		if (!customerEntity.isPresent()) {
			throw new ServiceException("Customer not found!", HttpStatus.NOT_FOUND);
		}
		
		Customer customer = customerEntity.get();
		
		Account account = new Account();
		account.setCustomerId(customerId);
		account.setAccountNo(util.generateAccNo());
		account.setAccountName(customer.getFirstName() + " " + customer.getLastName());
		account.setAccountType(AccountType.CURRENT.name());
		
		try {
			if (initialCredit != null && initialCredit > 0) {
				transactionService.createTransaction(account.getAccountNo(), initialCredit);
			}
			accountRepo.save(account);
		} catch (Exception e) {
			throw new ServiceException("Error occured while creating account!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
			
		Response response = new Response("Account created successfully!", HttpStatus.CREATED);
		return response;
	}

	@Override
	public List<AccountDTO> getAccountsByCustomerId(String customerId) {
		
		Optional<Customer> customerEntity = customerRepo.findByCustomerId(customerId);
		
		List<Account> accounts = accountRepo.findByCustomerId(customerEntity.get().getCustomerId());
		if (accounts.isEmpty()) {
			throw new ServiceException("Account Not Exists", HttpStatus.NO_CONTENT);
		}
		
		List<AccountDTO> accountDtos = new ArrayList<>();
		accounts.forEach(account -> {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setFirstName(customerEntity.get().getFirstName());
			accountDTO.setSurName(customerEntity.get().getLastName());
			
			List<FundTransactionDTO> transactions = new ArrayList<>();
			try { 
				transactions = transactionService.getTransactions(account.getAccountNo());
			} catch (Exception e) {
				throw new ServiceException("Error occurred while ", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Double balanceAmt = transactions.stream().collect(Collectors.summingDouble(FundTransactionDTO::getAmount));
			accountDTO.setBalance(balanceAmt);
			accountDTO.setTransactions(transactions);
			
			accountDtos.add(accountDTO);
		});
		
		return accountDtos;
	}


}
