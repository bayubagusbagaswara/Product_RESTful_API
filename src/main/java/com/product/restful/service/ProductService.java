package com.product.restful.service;

import com.product.restful.dto.product.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest createProductRequest);
    ProductResponse getProductById(String productId);
    List<ProductResponse> getAllProduct();
    ProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest) ;
    void deleteProduct(String productId) ;

    ListProductResponse listAllProduct(ListProductRequest listProductRequest);

    ProductResponse getProductByName(String name) ;
    List<ProductResponse> getProductByNameContaining(String name);
    List<ProductResponse> getProductByNameStartingWith(String name);
    List<ProductResponse> getProductByNameContainingOrderByName(String name);
    List<ProductResponse> getProductByNameContainingOrderByNameDesc(String name);

    List<ProductResponse> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax);
    List<ProductResponse> getProductByNameContainingOrderByPrice(String name);
    List<ProductResponse> getProductByNameContainingOrderByPriceDesc(String name);

    List<ProductResponse> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax);
    List<ProductResponse> getProductByPriceGreaterThanEqual(BigDecimal price);
    List<ProductResponse> getProductByPriceLessThanEqual(BigDecimal price);
}
