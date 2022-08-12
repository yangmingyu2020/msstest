package com.musinsa.msstest.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductExceptionResponse {

	private String message;
	private HttpStatus httpStatus;
	private final LocalDateTime dateTime = LocalDateTime.now();

	public String getDateTime() {
		return dateTime.toString();
	}
	
	public ProductExceptionResponse(String message, HttpStatus httpStatus){
		this.message = message;
		this.httpStatus = httpStatus;		
	}

}
