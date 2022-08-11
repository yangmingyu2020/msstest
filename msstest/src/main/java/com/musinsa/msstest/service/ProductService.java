package com.musinsa.msstest.service;

import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.PriceAndBrandResponseDto;

public interface ProductService {

	public MinimumPriceCombinationResponseDto getAllCategoryMinimumPrice();

	public PriceAndBrandResponseDto getAllCategoryMinimumPriceByBrand();

}
