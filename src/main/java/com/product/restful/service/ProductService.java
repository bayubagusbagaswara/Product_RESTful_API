package com.product.restful.service;

import com.product.restful.dto.product.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto createProduct(CreateProductRequest createProductRequest);
    ProductDto getProductById(String productId);
    List<ProductDto> getAllProduct();
    ProductDto updateProduct(String productId, UpdateProductRequest updateProductRequest) ;
    void deleteProduct(String productId) ;

    ListProductResponse listAllProduct(ListProductRequest listProductRequest);

    ProductDto getProductByName(String name) ;
    List<ProductDto> getProductByNameContaining(String name);
    List<ProductDto> getProductByNameStartingWith(String name);
    List<ProductDto> getProductByNameContainingOrderByName(String name);
    List<ProductDto> getProductByNameContainingOrderByNameDesc(String name);

    List<ProductDto> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax);
    List<ProductDto> getProductByNameContainingOrderByPrice(String name);
    List<ProductDto> getProductByNameContainingOrderByPriceDesc(String name);

    List<ProductDto> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax);
    List<ProductDto> getProductByPriceGreaterThanEqual(BigDecimal price);
    List<ProductDto> getProductByPriceLessThanEqual(BigDecimal price);
}
