package com.finance.fundtransactionservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response {
	
	private String message;
    private HttpStatus httpStatus;

}
