package com.product.restful.service;

import com.product.restful.dto.*;
import com.product.restful.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    GetProductResponse createProduct(CreateProductRequest createProductRequest);
    GetProductResponse getProductById(String productId) throws ProductNotFoundException;
    List<GetProductResponse> getAllProduct();
    GetProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest) throws ProductNotFoundException;
    void deleteProduct(String productId) throws ProductNotFoundException;

    GetAllProductResponse listAllProduct(GetAllProductRequest getAllProductRequest);

    GetProductResponse getProductByName(String name) throws ProductNotFoundException;
    List<GetProductResponse> getProductByNameContaining(String name);
    List<GetProductResponse> getProductByNameStartingWith(String name);
    List<GetProductResponse> getProductByNameContainingOrderByName(String name);
    List<GetProductResponse> getProductByNameContainingOrderByNameDesc(String name);

    List<GetProductResponse> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax);
    List<GetProductResponse> getProductByNameContainingOrderByPrice(String name);
    List<GetProductResponse> getProductByNameContainingOrderByPriceDesc(String name);

    List<GetProductResponse> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax);
    List<GetProductResponse> getProductByPriceGreaterThanEqual(BigDecimal price);
    List<GetProductResponse> getProductByPriceLessThanEqual(BigDecimal price);
}
