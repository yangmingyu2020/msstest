package com.musinsa.msstest.service;

import com.musinsa.msstest.dto.CreateProductRequestDto;
import com.musinsa.msstest.dto.MinAndMaxPriceProductResponseDto;
import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.PriceAndBrandResponseDto;
import com.musinsa.msstest.dto.UpdateProductRequestDto;
import com.musinsa.msstest.entity.ProductEntity;

public interface ProductService {

	public MinimumPriceCombinationResponseDto getAllCategoryMinimumPrice();

	public PriceAndBrandResponseDto getAllCategoryMinimumPriceByBrand();

	public MinAndMaxPriceProductResponseDto getMinAndMaxPriceByCategory(String category);

	public ProductEntity createProduct(CreateProductRequestDto createProductRequestDto);

	public ProductEntity updateProduct(UpdateProductRequestDto updateProductRequestDto);

	public Long deleteProduct(Long id);

}
