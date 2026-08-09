package com.fitfuel.backend.dto.request;

import java.math.BigDecimal;

import com.fitfuel.backend.enums.ProductCategory;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	
	@NotBlank(message = "Product name is required")
	 @Size(max = 150, message = "Product name cannot exceed 150 characters")
    private String name;

	 @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(
        value = "0.0",
        inclusive = false,
        message = "Product price must be greater than zero"
    )
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Product category is required")
    private ProductCategory category;

    @Min(value = 0, message = "Calories cannot be negative")
    private Integer calories;

    @DecimalMin(value = "0.0", message = "Protein cannot be negative")
    private Double protein;

    @DecimalMin(value = "0.0", message = "Carbohydrates cannot be negative")
    private Double carbohydrates;

    @DecimalMin(value = "0.0", message = "Fats cannot be negative")
    private Double fats;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;

    private Boolean active;

}
