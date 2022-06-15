package com.product.restful.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.product.restful.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private String description;

    @JsonIgnore
    private Instant createdAt;

    @JsonIgnore
    private Instant updatedAt;

    public static ProductDto fromEntity(Product product) {
        return new ProductDto(
                product.getId(), product.getName(), product.getPrice(),
                product.getQuantity(), product.getDescription(),
                product.getCreatedAt(), product.getUpdatedAt()
        );
    }

    public static List<ProductDto> fromEntityList(List<Product> productList) {
        return productList.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}
