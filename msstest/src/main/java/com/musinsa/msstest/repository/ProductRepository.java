package com.musinsa.msstest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musinsa.msstest.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
