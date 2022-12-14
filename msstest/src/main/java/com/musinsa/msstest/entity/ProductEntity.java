package com.musinsa.msstest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.musinsa.msstest.dto.CreateProductRequestDto;
import com.musinsa.msstest.dto.UpdateProductRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column
	private String brand;

	@Column
	private Long price;

	public int priceDiff(final ProductEntity other) {
		return (int) (price - other.price);
	}

	public ProductEntity() {
	}

	public ProductEntity(Category category, String brand, Long price) {
		this.category = category;
		this.brand = brand;
		this.price = price;
	}

	public static ProductEntity from(CreateProductRequestDto createProductRequestDto) {
		return new ProductEntity(createProductRequestDto.getCategory(), createProductRequestDto.getBrand(),
				createProductRequestDto.getPrice());
	}

	public static ProductEntity from(UpdateProductRequestDto updateProductRequestDto) {
		return new ProductEntity(updateProductRequestDto.getId(), updateProductRequestDto.getCategory(),
				updateProductRequestDto.getBrand(), updateProductRequestDto.getPrice());
	}

}