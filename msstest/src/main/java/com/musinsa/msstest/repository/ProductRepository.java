package com.musinsa.msstest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musinsa.msstest.entity.Category;
import com.musinsa.msstest.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByCategory(Category category);

	@Query(value = "SELECT DISTINCT p.brand FROM ProductEntity p")
	List<String> findDistinctBrand();

	List<ProductEntity> findByBrandAndCategory(String brand, Category category);

}
