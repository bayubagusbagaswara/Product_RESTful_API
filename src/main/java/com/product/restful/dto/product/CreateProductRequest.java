package com.product.restful.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name length max must be 100 characters")
    private String name;

    @NotNull(message = "Price can not null")
    @Min(value = 1, message = "Price must be greater than or equal to 1")
    private BigDecimal price;

    @NotNull(message = "Quantity can not null")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, message = "Description length minimum must be 10 characters")
    private String description;
}
