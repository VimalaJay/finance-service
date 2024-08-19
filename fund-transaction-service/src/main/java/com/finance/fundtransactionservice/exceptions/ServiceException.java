package com.finance.fundtransactionservice.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4674364607573863729L;
	private String message;
	private HttpStatus errorCode;

	public ServiceException(String message) {
		super(message);
	}

}
