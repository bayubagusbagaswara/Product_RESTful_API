package com.product.restful.service.impl;

import com.product.restful.dto.product.*;
import com.product.restful.exception.ResourceNotFoundException;
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
import java.util.List;

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

        ProductDTO productDto = productService.createProduct(createProductRequest);

        assertNotNull(productDto.getId());
        log.info("ID: {}", productDto.getId());
        log.info("Created At: {}", productDto.getCreatedAt());
    }

    @Test
    @Order(2)
    void getProductById() {
        String id = "macbook-pro-14-2021";
        ProductDTO product = productService.getProductById(id);

        assertSame(id, product.getId());
        assertNotNull(product);
        log.info("ID: {}", product.getId());
        log.info("Product: {}", product);
    }

    @Test
    @Order(3)
    void updateProduct() {
        String productId = "hp-pavilion-x360";
        UpdateProductRequest updateProductRequest = UpdateProductRequest.builder()
                .name("Test update")
                .price(new BigDecimal(200_000))
                .quantity(100)
                .description("Update description")
                .build();

        ProductDTO product = productService.updateProduct(productId, updateProductRequest);
        assertEquals(productId, product.getId());

        assertNotNull(product.getUpdatedAt());
        assertNotSame(product.getCreatedAt(), product.getUpdatedAt());

        log.info("ID: {}", product.getId());
        log.info("Created At: {}", product.getCreatedAt());
        log.info("Updated At: {}", product.getUpdatedAt());
    }

    @Test
    @Order(4)
    void deleteProduct() {
        String productId = "acer-nitro-5";
        productService.deleteProduct(productId);
        assertThrows(ResourceNotFoundException.class, () -> {
            ProductDTO product = productService.getProductById(productId);
        });
    }

    @Test
    @Order(5)
    void getAllProducts() {
        Integer pageSize = 5;
        Integer pageNo = 0;
        String sortBy = "name";
        String sortDir = "asc";

        ListProductRequest listProductRequest = ListProductRequest.builder()
                .pageSize(pageSize)
                .pageNo(pageNo)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        ListProductResponse listAllProduct = productService.listAllProduct(listProductRequest);
        assertEquals(pageSize, listAllProduct.getPageSize());
    }

    @Test
    @Order(6)
    void getProductByName() {
        String name = "apple macBook pro 14-inch 2021";
        ProductDTO product = productService.getProductByName(name);
        assertEquals(name, product.getName());
        log.info("Name = {}", product.getName());
    }

    @Test
    @Order(7)
    void getProductByNameContaining() {
        Integer totalProductMac = 0;
        String name = "mac";
        List<ProductDTO> productDTOS = productService.getProductByNameContaining(name);
        assertEquals(totalProductMac, productDTOS.size());
    }

    @Test
    @Order(8)
    void getProductByNameStartingWith() {
        Integer totalProductApple = 0;
        String name = "apple";
        List<ProductDTO> productDTOS = productService.getProductByNameStartingWith(name);
        assertEquals(totalProductApple, productDTOS.size());
    }

    @Test
    @Order(9)
    void getProductByNameContainingOrderByName() {
        Integer totalProductContainingName = 0;
        String name = "acer";
        List<ProductDTO> product = productService.getProductByNameContainingOrderByName(name);
        assertEquals(totalProductContainingName, product.size());
    }

    @Test
    @Order(10)
    void getProductByNameContainingOrderByNameDesc() {
        String name = "hp";
        List<ProductDTO> product = productService.getProductByNameContainingOrderByNameDesc(name);
    }

    @Test
    @Order(11)
    void getProductByNameContainingAndPriceBetween() {
        Integer totalProduct = 0;
        String name = "macbook";
        BigDecimal priceMin = new BigDecimal(13_000_000);
        BigDecimal priceMax = new BigDecimal(30_000_000);
        List<ProductDTO> product = productService.getProductByNameContainingAndPriceBetween(name, priceMin, priceMax);
        assertEquals(totalProduct, product.size());
    }

    @Test
    @Order(12)
    void getProductByNameContainingOrderByPriceDesc() {
        Integer totalProduct = 0;
        String name = "macbook";
        List<ProductDTO> product = productService.getProductByNameContainingOrderByPriceDesc(name);
        assertEquals(totalProduct, product.size());
    }

    @Test
    @Order(13)
    void getProductByNameContainingOrderByPrice() {
        Integer totalProduct = 0;
        String name = "macbook";
        List<ProductDTO> product = productService.getProductByNameContainingOrderByPrice(name);
        assertEquals(totalProduct, product.size());
    }

//    @Test
//    @Order(14)
//    void getProductByPriceBetween() {
//        Integer totalProduct = 0;
//        BigDecimal priceMin = new BigDecimal(10_000_000);
//        BigDecimal priceMax = new BigDecimal(20_000_000);
//        List<ProductDTO> product = productService.getProductByPriceBetween(priceMin, priceMax);
//        assertEquals(totalProduct, product.size());
//    }
//
//    @Test
//    @Order(15)
//    void getProductByPriceGreaterThanEqual() {
//        Integer totalProduct = 0;
//        BigDecimal price = new BigDecimal(10_000_000);
//        List<ProductDTO> product = productService.getProductByPriceGreaterThanEqual(price);
//        assertEquals(totalProduct, product.size());
//    }
//
//    @Test
//    @Order(16)
//    void getProductByPriceLessThanEqual() {
//        Integer totalProduct = 0;
//        BigDecimal price = new BigDecimal(10_000_000);
//        List<ProductDTO> product = productService.getProductByPriceLessThanEqual(price);
//        assertEquals(totalProduct, product.size());
//    }

}