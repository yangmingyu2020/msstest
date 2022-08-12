package com.musinsa.msstest.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductException extends RuntimeException {
	private HttpStatus httpStatus;

	public ProductException(String message) {
		super(message);
	}

	public ProductException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

}
