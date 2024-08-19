package com.finance.accountservice.external.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.accountservice.dto.FundTransactionDTO;
import com.finance.accountservice.exceptions.ServiceException;

@Component
public class TransactionRestService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${transaction.service.url}")
	private String transactionServiceURL;

	public List<FundTransactionDTO> getTransactions(String accountId) {

		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<Object> response = restTemplate.exchange(transactionServiceURL + "/transaction/" + accountId,
				HttpMethod.GET, entity, Object.class);
		
		if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
			throw new ServiceException("Transactions not found for user!");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		List<FundTransactionDTO> result = mapper.convertValue(response.getBody(),
				new com.fasterxml.jackson.core.type.TypeReference<List<FundTransactionDTO>>() {
				});
		return result;

	}

	public void createTransaction(String accountId, Double amount) {

		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

		FundTransactionDTO fundTransactionDTO = new FundTransactionDTO();
		fundTransactionDTO.setAccountId(accountId);
		fundTransactionDTO.setAmount(amount);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<FundTransactionDTO> request = new HttpEntity<>(fundTransactionDTO, headers);

		String url = transactionServiceURL + "/add?accountId=" + accountId + "&amount=" + amount;

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);

	}

}
