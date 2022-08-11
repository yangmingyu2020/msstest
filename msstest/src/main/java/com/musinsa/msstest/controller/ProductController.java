package com.musinsa.msstest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.msstest.dto.MinAndMaxPriceProductResponseDto;
import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.PriceAndBrandResponseDto;
import com.musinsa.msstest.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {

	private final ProductService productService;

	@GetMapping("/all/minimum")
	public ResponseEntity<MinimumPriceCombinationResponseDto> getAllCategoryMinimumPrice() {
		return ResponseEntity.ok(productService.getAllCategoryMinimumPrice());
	}

	@GetMapping("all/minimum/brand")
	public ResponseEntity<PriceAndBrandResponseDto> getAllCategoryMinimumPriceByBrand() {
		return ResponseEntity.ok(productService.getAllCategoryMinimumPriceByBrand());
	}

	@GetMapping("price/min-and-max/{category}")
	public ResponseEntity<MinAndMaxPriceProductResponseDto> getMinAndMaxPriceByCategory(@PathVariable String category) {
		return ResponseEntity.ok(productService.getMinAndMaxPriceByCategory(category));
	}

}
