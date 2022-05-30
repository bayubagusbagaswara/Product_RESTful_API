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
public class ProductResponse {

    private String id;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private String description;

    private String createdBy;

    private Instant createdAt;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setDescription(product.getDescription());
        productResponse.setCreatedBy(product.getCreatedBy());
        productResponse.setCreatedAt(product.getCreatedAt());
        return productResponse;
    }

    public static List<ProductResponse> fromProductList(List<Product> productList) {
        return productList.stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }
}
