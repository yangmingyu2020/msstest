package com.musinsa.msstest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musinsa.msstest.dto.CreateProductRequestDto;
import com.musinsa.msstest.dto.MinAndMaxPriceProductResponseDto;
import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.PriceAndBrandResponseDto;
import com.musinsa.msstest.dto.ProductResponseDto;
import com.musinsa.msstest.dto.UpdateProductRequestDto;
import com.musinsa.msstest.entity.Category;
import com.musinsa.msstest.entity.ProductEntity;
import com.musinsa.msstest.exception.ProductException;
import com.musinsa.msstest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public MinimumPriceCombinationResponseDto getAllCategoryMinimumPrice() {

		List<ProductResponseDto> productEntities = new ArrayList<>();
		Long totalPrice = 0L;

		for (Category category : Category.values()) {
			List<ProductEntity> productEntityList = productRepository.findByCategory(category);

			ProductEntity minimumProduct = productEntityList.stream().min(ProductEntity::priceDiff).get();

			totalPrice += minimumProduct.getPrice();

			productEntities.add(ProductResponseDto.from(minimumProduct));
		}

		return MinimumPriceCombinationResponseDto.of(productEntities, totalPrice);
	}

	@Override
	public PriceAndBrandResponseDto getAllCategoryMinimumPriceByBrand() {

		List<String> brands = productRepository.findDistinctBrand();
		List<PriceAndBrandResponseDto> priceAndBrandResponseDtos = new ArrayList<>();

		for (String brand : brands) {
			Long totalPrice = 0L;
			for (Category category : Category.values()) {
				List<ProductEntity> productEntities = productRepository.findByBrandAndCategory(brand, category);

				ProductEntity minimumProduct = productEntities.stream().min(ProductEntity::priceDiff).get();

				totalPrice += minimumProduct.getPrice();
			}
			priceAndBrandResponseDtos.add(PriceAndBrandResponseDto.of(brand, totalPrice));
		}

		return priceAndBrandResponseDtos.stream().min(PriceAndBrandResponseDto::totalPriceDiff).get();
	}

	@Override
	public MinAndMaxPriceProductResponseDto getMinAndMaxPriceByCategory(String category) {

		Category categoryEnum = null;

		for (Category category1 : Category.values()) {
			if (category1.toString().equals(category)) {
				categoryEnum = category1;
			}
		}

		if (categoryEnum == null) {
			throw new ProductException("해당 카테고리는 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}

		List<ProductEntity> productEntities = productRepository.findByCategory(categoryEnum);

		ProductEntity minimumProduct = productEntities.stream().min(ProductEntity::priceDiff).get();

		ProductEntity maximumProduct = productEntities.stream().max(ProductEntity::priceDiff).get();

		return MinAndMaxPriceProductResponseDto.of(minimumProduct.getBrand(), minimumProduct.getPrice(),
				maximumProduct.getBrand(), maximumProduct.getPrice());

	}

	@Override
	public ProductEntity createProduct(CreateProductRequestDto createProductRequestDto) {
		if (createProductRequestDto.getBrand() == null || createProductRequestDto.getPrice() == null
				|| createProductRequestDto.getCategory() == null) {
			throw new ProductException("상품의 정보 값이 비어있습니다.", HttpStatus.BAD_REQUEST);
		}

		if (createProductRequestDto.getPrice() < 0) {
			throw new ProductException("상품의 가격이 0 미만입니다.", HttpStatus.BAD_REQUEST);
		}

		ProductEntity productEntity = ProductEntity.from(createProductRequestDto);
		return productRepository.save(productEntity);
	}

	@Override
	public ProductEntity updateProduct(UpdateProductRequestDto updateProductRequestDto) {
		if (updateProductRequestDto.getId() == null) {
			throw new ProductException("수정 하기 위해 상품 ID는 필수 값입니다.", HttpStatus.CONFLICT);
		}

		productRepository.findById(updateProductRequestDto.getId())
				.orElseThrow(() -> new ProductException("해당 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND));

		ProductEntity productEntity = ProductEntity.from(updateProductRequestDto);
		return productRepository.save(productEntity);
	}

	@Override
	public Long deleteProduct(Long id) {
		productRepository.findById(id)
				.orElseThrow(() -> new ProductException("해당 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND));
		productRepository.deleteById(id);
		return id;
	}

}
