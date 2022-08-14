package com.product.restful.mapper;

import com.product.restful.dto.product.ProductDTO;
import com.product.restful.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private ProductDTO mapFromProduct(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(),
                product.getQuantity(), product.getDescription(),
                product.getCreatedAt(), product.getUpdatedAt()
        );
    }

    private List<ProductDTO> mapFromProductList(List<Product> productList) {
        return productList.stream()
                .map(this::mapFromProduct)
                .collect(Collectors.toList());
    }
}
