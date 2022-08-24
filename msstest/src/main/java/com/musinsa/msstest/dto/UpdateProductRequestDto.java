package com.musinsa.msstest.dto;

import com.musinsa.msstest.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductRequestDto {
	private Long id;
	private Category category;
	private String brand;
	private Long price;

	UpdateProductRequestDto() {

	}

}
