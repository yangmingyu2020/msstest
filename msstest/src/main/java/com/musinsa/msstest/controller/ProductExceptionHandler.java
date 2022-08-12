package com.musinsa.msstest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.musinsa.msstest.dto.ProductExceptionResponse;
import com.musinsa.msstest.exception.ProductException;

@RestControllerAdvice
public class ProductExceptionHandler {

	@ExceptionHandler(ProductException.class)
	protected ResponseEntity<ProductExceptionResponse> handleProductException(ProductException e) {
		ProductExceptionResponse productExceptionResponse = new ProductExceptionResponse(e.getMessage(),
				e.getHttpStatus());
		return ResponseEntity.status(e.getHttpStatus()).body(productExceptionResponse);
	}
}
