package com.musinsa.msstest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinAndMaxPriceProductResponseDto {

	private String minimumBrand;
	private Long minimumPrice;
	private String maximumBrand;
	private Long maximumPrice;

	public static MinAndMaxPriceProductResponseDto of(String minimumBrand, Long minimumPrice, String maximumBrand,
			Long maximumPrice) {
		return new MinAndMaxPriceProductResponseDto(minimumBrand, minimumPrice, maximumBrand, maximumPrice);
	}

}
