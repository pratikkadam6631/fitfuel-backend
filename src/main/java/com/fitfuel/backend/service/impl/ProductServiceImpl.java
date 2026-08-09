package com.fitfuel.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fitfuel.backend.dto.request.ProductRequest;
import com.fitfuel.backend.dto.response.ProductResponse;
import com.fitfuel.backend.entity.Product;
import com.fitfuel.backend.mapper.ProductMapper;
import com.fitfuel.backend.repository.ProductRepository;
import com.fitfuel.backend.service.ProductService;
import com.fitfuel.backend.exception.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService{
	
	 private final ProductRepository productRepository;
	    private final ProductMapper productMapper;

	    public ProductServiceImpl(
	            ProductRepository productRepository,
	            ProductMapper productMapper) {

	        this.productRepository = productRepository;
	        this.productMapper = productMapper;
	    }

	    @Override
	    public ProductResponse createProduct(ProductRequest request) {

	        Product product = productMapper.toEntity(request);

	        Product savedProduct = productRepository.save(product);

	        return productMapper.toResponse(savedProduct);
	    }

	    @Override
	    public List<ProductResponse> getAllProducts() {

	        List<Product> products = productRepository.findAll();

	        List<ProductResponse> responses = new ArrayList<>();

	        for (Product product : products) {
	            ProductResponse response = productMapper.toResponse(product);
	            responses.add(response);
	        }

	        return responses;
	    }

	    @Override
	    public ProductResponse getProductById(Long id) {

	        Product product = productRepository.findById(id)
	        		.orElseThrow(() ->
	                new ProductNotFoundException(
	                        "Product not found with id: " + id
	                )
	        );

	        return productMapper.toResponse(product);
	    }

	    @Override
	    public ProductResponse updateProduct(
	            Long id,
	            ProductRequest request) {

	        Product existingProduct = productRepository.findById(id)
	                .orElseThrow(() ->
	                        new ProductNotFoundException(
	                                "Product not found with id: " + id
	                        )
	                );

	        productMapper.updateEntity(request, existingProduct);

	        Product updatedProduct =
	                productRepository.save(existingProduct);

	        return productMapper.toResponse(updatedProduct);
	    }

	    @Override
	    public void deleteProduct(Long id) {

	        Product product = productRepository.findById(id)
	                .orElseThrow(() ->
	                        new ProductNotFoundException(
	                                "Product not found with id: " + id
	                        )
	                );

	        productRepository.delete(product);
	    }
}
