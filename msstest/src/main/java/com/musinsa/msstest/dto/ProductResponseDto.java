package com.musinsa.msstest.dto;

import com.musinsa.msstest.entity.Category;
import com.musinsa.msstest.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {

	private Category category;
	private String brand;
	private Long price;

	public static ProductResponseDto from(ProductEntity productEntity) {
		return new ProductResponseDto(productEntity.getCategory(), productEntity.getBrand(), productEntity.getPrice());
	}

}
