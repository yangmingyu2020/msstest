package com.musinsa.msstest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.ProductResponseDto;
import com.musinsa.msstest.entity.ProductEntity;
import com.musinsa.msstest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Override
	public MinimumPriceCombinationResponseDto getAllCategoryMinimumPrice() {

		List<String> categories = productRepository.findDistinctCategory();
		List<ProductResponseDto> productEntities = new ArrayList<>();
		Long totalPrice = 0L;

		for (String category : categories) {
			List<ProductEntity> productEntityList = productRepository.findByCategory(category);

			ProductEntity minimumProduct = productEntityList.stream().min(ProductEntity::priceDiff).get();

			totalPrice += minimumProduct.getPrice();

			productEntities.add(ProductResponseDto.from(minimumProduct));
		}

		return MinimumPriceCombinationResponseDto.of(productEntities, totalPrice);
	}

}
