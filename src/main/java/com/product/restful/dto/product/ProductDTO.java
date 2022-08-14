package com.product.restful.dto.product;

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
public class ProductDTO {

    private String id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    public static ProductDTO mapFromEntity(Product product) {
        return new ProductDTO(
                product.getId(), product.getName(), product.getPrice(),
                product.getQuantity(), product.getDescription(),
                product.getCreatedAt(), product.getUpdatedAt()
        );
    }

    public static List<ProductDTO> mapFromEntityList(List<Product> productList) {
        return productList.stream()
                .map(ProductDTO::mapFromEntity)
                .collect(Collectors.toList());
    }
}
