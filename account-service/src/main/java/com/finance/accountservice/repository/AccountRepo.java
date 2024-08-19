package com.finance.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.accountservice.Entity.Account;

public interface AccountRepo extends JpaRepository<Account, String> {
	
	List<Account> findByCustomerId(String customerId);

}
