package com.finance.accountservice.Entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data public class Account implements Serializable {
	
	private static final long serialVersionUID = -2909497324318643297L;

	@Id
	private String accountNo;
	private String accountName;
	private String accountType;
	private String customerId;
	
}
