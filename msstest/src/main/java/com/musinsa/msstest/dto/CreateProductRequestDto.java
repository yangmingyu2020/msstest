package com.musinsa.msstest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductRequestDto {
	private String category;
	private String brand;
	private Long price;

	CreateProductRequestDto() {

	}

}
