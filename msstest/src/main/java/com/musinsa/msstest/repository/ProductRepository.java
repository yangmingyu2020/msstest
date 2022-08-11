package com.musinsa.msstest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musinsa.msstest.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByCategory(String category);

	@Query(value = "SELECT DISTINCT p.category FROM ProductEntity p")
	List<String> findDistinctCategory();

}
