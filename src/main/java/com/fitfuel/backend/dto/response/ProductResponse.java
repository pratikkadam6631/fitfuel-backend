package com.fitfuel.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fitfuel.backend.enums.ProductCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
	
	private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private ProductCategory category;

    private Integer calories;

    private Double protein;

    private Double carbohydrates;

    private Double fats;

    private String imageUrl;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
