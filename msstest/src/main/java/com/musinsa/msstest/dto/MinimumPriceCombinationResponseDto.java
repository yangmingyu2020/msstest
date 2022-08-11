package com.musinsa.msstest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinimumPriceCombinationResponseDto {

	private List<ProductResponseDto> products;
	private Long totalPrice;

	public static MinimumPriceCombinationResponseDto of(List<ProductResponseDto> products, Long totalPrice) {
		return new MinimumPriceCombinationResponseDto(products, totalPrice);
	}

}
