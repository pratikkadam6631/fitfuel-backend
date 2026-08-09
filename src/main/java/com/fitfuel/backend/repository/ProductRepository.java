package com.fitfuel.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitfuel.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
