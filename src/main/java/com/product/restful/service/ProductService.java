package com.product.restful.service;

import com.product.restful.dto.product.*;
import com.product.restful.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest createProductRequest);
    ProductDTO getProductById(String productId);
    List<ProductDTO> getAllProduct();
    ProductDTO updateProduct(String productId, UpdateProductRequest updateProductRequest) ;
    void deleteProduct(String productId) ;

    ListProductResponse listAllProduct(ListProductRequest listProductRequest);

    ProductDTO getProductByName(String name) ;
    List<ProductDTO> getProductByNameContaining(String name);
    List<ProductDTO> getProductByNameStartingWith(String name);
    List<ProductDTO> getProductByNameContainingOrderByName(String name);
    List<ProductDTO> getProductByNameContainingOrderByNameDesc(String name);

    List<ProductDTO> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax);
    List<ProductDTO> getProductByNameContainingOrderByPrice(String name);
    List<ProductDTO> getProductByNameContainingOrderByPriceDesc(String name);

    List<ProductDTO> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax);
    List<ProductDTO> getProductByPriceGreaterThanEqual(BigDecimal price);
    List<ProductDTO> getProductByPriceLessThanEqual(BigDecimal price);

    Product getProductByIdNeW(String id);
}
