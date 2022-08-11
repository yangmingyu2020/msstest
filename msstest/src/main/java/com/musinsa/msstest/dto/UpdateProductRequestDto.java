package com.musinsa.msstest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductRequestDto {
	private Long id;
	private String category;
	private String brand;
	private Long price;

	UpdateProductRequestDto() {

	}
	
}
