package com.musinsa.msstest.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductException extends RuntimeException {
	private String message;
	private HttpStatus httpStatus;
}
