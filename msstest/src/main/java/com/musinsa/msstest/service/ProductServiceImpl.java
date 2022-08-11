package com.musinsa.msstest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musinsa.msstest.dto.CreateProductRequestDto;
import com.musinsa.msstest.dto.MinAndMaxPriceProductResponseDto;
import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.PriceAndBrandResponseDto;
import com.musinsa.msstest.dto.ProductResponseDto;
import com.musinsa.msstest.dto.UpdateProductRequestDto;
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

	@Override
	public PriceAndBrandResponseDto getAllCategoryMinimumPriceByBrand() {

		List<String> categories = productRepository.findDistinctCategory();
		List<String> brands = productRepository.findDistinctBrand();
		List<PriceAndBrandResponseDto> priceAndBrandResponseDtos = new ArrayList<>();

		for (String brand : brands) {
			Long totalPrice = 0L;
			for (String category : categories) {
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

		List<String> categories = productRepository.findDistinctCategory();

		if (!categories.contains(category)) {
			throw new RuntimeException("해당 카테고리는 존재하지 않습니다.");
		}

		List<ProductEntity> productEntities = productRepository.findByCategory(category);

		ProductEntity minimumProduct = productEntities.stream().min(ProductEntity::priceDiff).get();

		ProductEntity maximumProduct = productEntities.stream().max(ProductEntity::priceDiff).get();

		return MinAndMaxPriceProductResponseDto.of(minimumProduct.getBrand(), minimumProduct.getPrice(),
				maximumProduct.getBrand(), maximumProduct.getPrice());

	}

	@Override
	public ProductEntity createProduct(CreateProductRequestDto createProductRequestDto) {
		ProductEntity productEntity = ProductEntity.from(createProductRequestDto);
		return productRepository.save(productEntity);
	}

	@Override
	public ProductEntity updateProduct(UpdateProductRequestDto updateProductRequestDto) {
		if (updateProductRequestDto.getId() == null) {
			throw new RuntimeException("수정 하기 위해 상품 ID는 필수 값입니다.");
		}

		productRepository.findById(updateProductRequestDto.getId())
				.orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않습니다."));

		ProductEntity productEntity = ProductEntity.from(updateProductRequestDto);
		return productRepository.save(productEntity);
	}

	@Override
	public Long deleteProduct(Long id) {
		productRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않습니다."));
		productRepository.deleteById(id);
		return id;
	}

}
