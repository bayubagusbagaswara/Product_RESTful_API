package com.product.restful.service;

import com.product.restful.dto.*;
import com.product.restful.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest createProductRequest);
    ProductResponse getProductById(String productId) throws ProductNotFoundException;
    List<ProductResponse> getAllProduct();
    ProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest) throws ProductNotFoundException;
    void deleteProduct(String productId) throws ProductNotFoundException;

    ListProductResponse listAllProduct(ListProductRequest listProductRequest);

    ProductResponse getProductByName(String name) throws ProductNotFoundException;
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
