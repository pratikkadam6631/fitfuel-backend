package com.fitfuel.backend.service;

import java.util.List;

import com.fitfuel.backend.dto.request.ProductRequest;
import com.fitfuel.backend.dto.response.ProductResponse;

public interface ProductService {
	

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

}
