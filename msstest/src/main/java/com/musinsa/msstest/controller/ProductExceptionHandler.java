package com.musinsa.msstest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.musinsa.msstest.exception.ProductException;

@RestControllerAdvice
public class ProductExceptionHandler {
	@ExceptionHandler(ProductException.class)
	protected ResponseEntity<?> handleProductException(ProductException e) {
		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}
}
