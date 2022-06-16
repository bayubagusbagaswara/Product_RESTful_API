package com.product.restful.service.impl;

import com.product.restful.dto.product.CreateProductRequest;
import com.product.restful.dto.product.ProductDto;
import com.product.restful.service.ProductService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImplTest.class);

    @Autowired
    ProductService productService;

    @Test
    @Order(1)
    void createProduct() {
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .name("Test Service Product")
                .price(new BigDecimal(134_900_000))
                .quantity(50)
                .description("This is test service product description")
                .build();
        ProductDto productDto = productService.createProduct(createProductRequest);
        assertNotNull(productDto.getId());

        log.info("ID: {}", productDto.getId());
        log.info("Created At: {}", productDto.getCreatedAt());
    }
//
//    @Test
//    @Order(2)
//    void getProductById() throws ProductNotFoundException {
//        String id = "macbook-pro-14-2021";
//        GetProductResponse getProductResponse = productService.getProductById(id);
//        assertSame(id, getProductResponse.getId());
//        assertNotNull(getProductResponse);
//
//        log.info("ID: {}", getProductResponse.getId());
//        log.info("Name: {}", getProductResponse.getName());
//        log.info("Price: {}", getProductResponse.getPrice());
//        log.info("Quantity: {}", getProductResponse.getQuantity());
//        log.info("Created At: {}", getProductResponse.getCreatedAt());
//        log.info("Updated At: {}", getProductResponse.getUpdatedAt());
//    }
//
//    @Test
//    @Order(3)
//    void updateProduct() throws ProductNotFoundException {
//        UpdateProductRequest updateProductRequest = UpdateProductRequest.builder()
//                .name("Test Update Service")
//                .price(new BigDecimal(12_345_000))
//                .quantity(8985)
//                .description("This is test update service product description")
//                .build();
//        String productId= "hp-pavilion-x360";
//        GetProductResponse getProductResponse = productService.updateProduct(productId, updateProductRequest);
//        assertEquals(productId, getProductResponse.getId());
//        assertNotNull(getProductResponse.getUpdatedAt());
//        assertNotSame(getProductResponse.getCreatedAt(), getProductResponse.getUpdatedAt());
//
//        log.info("ID: {}", getProductResponse.getId());
//        log.info("Name: {}", getProductResponse.getName());
//        log.info("Price: {}", getProductResponse.getPrice());
//        log.info("Quantity: {}", getProductResponse.getQuantity());
//        log.info("Description: {}", getProductResponse.getDescription());
//        log.info("Created At: {}", getProductResponse.getCreatedAt());
//        log.info("Created By: {}", getProductResponse.getCreatedBy());
//        log.info("Updated At: {}", getProductResponse.getUpdatedAt());
//        log.info("Updated By: {}", getProductResponse.getUpdatedBy());
//    }
//
//    @Test
//    @Order(4)
//    void deleteProduct() throws ProductNotFoundException {
//        String productId = "acer-nitro-5";
//        productService.deleteProduct(productId);
//    }
//
//    @Test
//    @Order(5)
//    void getAllProducts() {
//        Integer pageSize = 5;
//        Integer pageNo = 0;
//        String sortBy = "name";
//        String sortDir = "asc";
//        GetAllProductRequest getAllProductRequest = GetAllProductRequest.builder()
//                .pageSize(pageSize)
//                .pageNo(pageNo)
//                .sortBy(sortBy)
//                .sortDir(sortDir)
//                .build();
//        GetAllProductResponse getAllProductResponse = productService.listAllProduct(getAllProductRequest);
//
//        List<GetProductResponse> responses = getAllProductResponse.getGetProductResponses();
//        log.info("Size = {}", getAllProductResponse.getGetProductResponses().size());
//        for (GetProductResponse product : responses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("======================");
//        }
//        log.info("Total Element = {}", getAllProductResponse.getTotalElements());
//        log.info("Total Page = {}", getAllProductResponse.getTotalPages());
//    }
//
//    @Test
//    @Order(6)
//    void getProductByName() throws ProductNotFoundException {
//        String name = "apple macBook pro 14-inch 2021";
//        GetProductResponse productResponses = productService.getProductByName(name);
//        log.info("Name = {}", productResponses.getName());
//        log.info("Price = {}", productResponses.getPrice());
//    }
//
//    @Test
//    @Order(7)
//    void getProductByNameContaining() {
//        String name = "mac";
//        List<GetProductResponse> productResponses = productService.getProductByNameContaining(name);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("================");
//        }
//    }
//
//    @Test
//    @Order(8)
//    void getProductByNameStartingWith() {
//        String name = "apple";
//        List<GetProductResponse> productResponses = productService.getProductByNameStartingWith(name);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("================");
//        }
//    }
//
//    @Test
//    @Order(9)
//    void getProductByNameContainingOrderByName() {
//        String name = "acer";
//        List<GetProductResponse> productResponses = productService.getProductByNameContainingOrderByName(name);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("================");
//        }
//    }
//
//    @Test
//    @Order(10)
//    void getProductByNameContainingOrderByNameDesc() {
//        String name = "hp";
//        List<GetProductResponse> productResponses = productService.getProductByNameContainingOrderByNameDesc(name);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("================");
//        }
//    }
//
//    @Test
//    @Order(11)
//    void getProductByNameContainingAndPriceBetween() {
//        String name = "macbook";
//        BigDecimal priceMin = new BigDecimal(13_000_000);
//        BigDecimal priceMax = new BigDecimal(30_000_000);
//        List<GetProductResponse> productResponses = productService.getProductByNameContainingAndPriceBetween(name, priceMin, priceMax);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("====================");
//        }
//    }
//
//    @Test
//    @Order(12)
//    void getProductByNameContainingOrderByPriceDesc() {
//        String name = "macbook";
//        List<GetProductResponse> productResponses = productService.getProductByNameContainingOrderByPriceDesc(name);
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("====================");
//        }
//    }
//
//    @Test
//    @Order(13)
//    void getProductByNameContainingOrderByPrice() {
//        String name = "macbook";
//        List<GetProductResponse> productResponses = productService.getProductByNameContainingOrderByPrice(name);
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("====================");
//        }
//    }
//
//    @Test
//    @Order(14)
//    void getProductByPriceBetween() {
//        BigDecimal priceMin = new BigDecimal(10_000_000);
//        BigDecimal priceMax = new BigDecimal(20_000_000);
//        List<GetProductResponse> productResponses = productService.getProductByPriceBetween(priceMin, priceMax);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("====================");
//        }
//    }
//
//    @Test
//    @Order(15)
//    void getProductByPriceGreaterThanEqual() {
//        BigDecimal price = new BigDecimal(10_000_000);
//        List<GetProductResponse> productResponses = productService.getProductByPriceGreaterThanEqual(price);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("====================");
//        }
//    }
//
//    @Test
//    @Order(16)
//    void getProductByPriceLessThanEqual() {
//        BigDecimal price = new BigDecimal(10_000_000);
//        List<GetProductResponse> productResponses = productService.getProductByPriceLessThanEqual(price);
//        log.info("Size = {}", productResponses.size());
//        for (GetProductResponse product : productResponses) {
//            log.info("Name = {}", product.getName());
//            log.info("Price = {}", product.getPrice());
//            log.info("====================");
//        }
//    }

}