package com.finance.accountservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {
	
	@Id
	private String customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	

}
