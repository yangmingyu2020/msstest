package com.musinsa.msstest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.musinsa.msstest.dto.MinimumPriceCombinationResponseDto;
import com.musinsa.msstest.dto.PriceAndBrandResponseDto;
import com.musinsa.msstest.entity.ProductEntity;
import com.musinsa.msstest.exception.ProductException;
import com.musinsa.msstest.repository.ProductRepository;

@SpringBootTest
@ActiveProfiles("test")
public class serviceTest {

	@Autowired
	protected ProductRepository productRepository;

	@Autowired
	protected ProductService productService;

	@BeforeEach
	void initData() {
		productRepository.deleteAll();
	}

	protected ProductEntity createProduct(String category, String brand, Long price) {
		ProductEntity newProductEntity = ProductEntity.builder().category(category).brand(brand).price(price).build();
		return productRepository.save(newProductEntity);
	}

	@Test
	@DisplayName("한 브랜드에서 특정 카테고리 최저가 조회 테스트")
	void test1() {
		// given
		createProduct("상의", "A", 11200L);
		createProduct("상의", "A", 10500L);

		// when
		List<ProductEntity> productEntities = productRepository.findByBrandAndCategory("A", "상의");
		ProductEntity minimumProduct = productEntities.stream().min(ProductEntity::priceDiff).get();

		// then
		assertThat(minimumProduct.getPrice()).isEqualTo(10500L);
	}

	@Test
	@DisplayName("모든 카테고리의 상품 최저가 및 브랜드 조회 테스트")
	void test2() {
		// given
		createProduct("상의", "A", 11200L);
		createProduct("상의", "B", 10500L);
		createProduct("아우터", "A", 5500L);
		createProduct("아우터", "B", 5900L);

		// when
		MinimumPriceCombinationResponseDto minimumPriceCombinationResponseDto = productService
				.getAllCategoryMinimumPrice();

		// then
		assertThat(minimumPriceCombinationResponseDto.getTotalPrice()).isEqualTo(16000L);
		assertThat(minimumPriceCombinationResponseDto.getProducts().get(0).getBrand()).isEqualTo("B");
	}

	@Test
	@DisplayName("브랜드별 최저가 조회 테스트")
	void test3() {
		// given
		createProduct("상의", "A", 11200L);
		createProduct("상의", "B", 10500L);
		createProduct("아우터", "A", 5500L);
		createProduct("아우터", "B", 5900L);
		createProduct("하의", "A", 4200L);
		createProduct("하의", "B", 3800L);

		List<String> brands = new ArrayList<String>();
		brands.add("A");
		brands.add("B");

		List<String> categories = new ArrayList<String>();
		categories.add("상의");
		categories.add("아우터");
		categories.add("하의");

		List<PriceAndBrandResponseDto> priceAndBrandResponseDtos = new ArrayList<>();

		// when
		for (String brand : brands) {
			Long totalPrice = 0L;
			for (String category : categories) {
				List<ProductEntity> productEntities = productRepository.findByBrandAndCategory(brand, category);
				ProductEntity minimumProduct = productEntities.stream().min(ProductEntity::priceDiff).get();
				totalPrice += minimumProduct.getPrice();
			}
			priceAndBrandResponseDtos.add(PriceAndBrandResponseDto.of(brand, totalPrice));
		}

		// then
		assertAll(() -> assertThat(priceAndBrandResponseDtos.size()).isEqualTo(2),
				() -> assertThat(priceAndBrandResponseDtos.get(0).getTotalPrice()).isEqualTo(20900),
				() -> assertThat(priceAndBrandResponseDtos.get(0).getBrand()).isEqualTo("A"),
				() -> assertThat(priceAndBrandResponseDtos.get(1).getTotalPrice()).isEqualTo(20200),
				() -> assertThat(priceAndBrandResponseDtos.get(1).getBrand()).isEqualTo("B"));

	}

	@Test
	@DisplayName("브랜드별 최저가 비교 조회 테스트")
	void test4() {
		// given
		createProduct("상의", "A", 11200L);
		createProduct("상의", "B", 10500L);
		createProduct("아우터", "A", 5500L);
		createProduct("아우터", "B", 5900L);
		createProduct("하의", "A", 4200L);
		createProduct("하의", "B", 3800L);

		// when
		PriceAndBrandResponseDto priceAndBrandResponseDto = productService.getAllCategoryMinimumPriceByBrand();

		// then
		assertThat(priceAndBrandResponseDto.getTotalPrice()).isEqualTo(20200L);
		assertThat(priceAndBrandResponseDto.getBrand()).isEqualTo("B");

	}

	@Test
	@DisplayName("카테고리 이름으로 최소, 최대 조회 테스트")
	void test5() {
		// given
		createProduct("아우터", "A", 5500L);
		createProduct("아우터", "B", 5900L);
		createProduct("아우터", "C", 6200L);

		// when
		List<ProductEntity> productEntities = productRepository.findByCategory("아우터");
		ProductEntity minimumProduct = productEntities.stream().min(ProductEntity::priceDiff).get();
		ProductEntity maximumProduct = productEntities.stream().max(ProductEntity::priceDiff).get();

		// then
		assertThat(productEntities).isNotNull();
		assertThat(minimumProduct.getPrice()).isEqualTo(5500L);
		assertThat(maximumProduct.getPrice()).isEqualTo(6200L);

	}

	@Test
	@DisplayName("존재하지 않는 카테고리 이름으로 최소, 최대 조회시 예외발생 테스트")
	void test6() {
		// given
		createProduct("아우터", "A", 5500L);
		createProduct("아우터", "B", 5900L);
		createProduct("아우터", "C", 6200L);

		// when
		Throwable thrown = catchThrowable(() -> productService.getMinAndMaxPriceByCategory("상의"));

		// then
		assertThat(thrown).isInstanceOf(ProductException.class);
	}

}
