package com.fitfuel.backend.mapper;

import org.springframework.stereotype.Component;

import com.fitfuel.backend.dto.request.ProductRequest;
import com.fitfuel.backend.dto.response.ProductResponse;
import com.fitfuel.backend.entity.Product;

@Component
public class ProductMapper {

    // Converts ProductRequest DTO into Product entity
    public Product toEntity(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setCalories(request.getCalories());
        product.setProtein(request.getProtein());
        product.setCarbohydrates(request.getCarbohydrates());
        product.setFats(request.getFats());
        product.setImageUrl(request.getImageUrl());

        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }

        return product;
    }

    // Converts Product entity into ProductResponse DTO
    public ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setCalories(product.getCalories());
        response.setProtein(product.getProtein());
        response.setCarbohydrates(product.getCarbohydrates());
        response.setFats(product.getFats());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }

    // Copies updated request values into an existing Product entity
    public void updateEntity(
            ProductRequest request,
            Product product) {

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setCalories(request.getCalories());
        product.setProtein(request.getProtein());
        product.setCarbohydrates(request.getCarbohydrates());
        product.setFats(request.getFats());
        product.setImageUrl(request.getImageUrl());

        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }
    }
}