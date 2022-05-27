package com.product.restful.repository;

import com.product.restful.dto.UpdateProductRequest;
import com.product.restful.entity.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {
//
//    private final static Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);
//
//    @Autowired
//    ProductRepository productRepository;
//
//    @Test
//    @Order(1)
//    void createProduct() {
//        Product product = Product.builder()
//                .name("Test Repository")
//                .price(new BigDecimal("55550000"))
//                .quantity(5550)
//                .build();
//
//        assertNull(product.getId());
//        productRepository.save(product);
//        assertNotNull(product.getId());
//        log.info("ID: {}", product.getId());
//        log.info("Name: {}", product.getName());
//        log.info("Price: {}", product.getPrice());
//        log.info("Quantity: {}", product.getQuantity());
//        log.info("Created At: {}", product.getCreatedAt());
//    }
//
//    @Test
//    @Order(2)
//    void getProductById() {
//        // masukkan id product yang sudah tersimpan
//        String id = "macbook-pro-14-2021";
//        Optional<Product> product = productRepository.findById(id);
//        assertTrue(product.isPresent(), "Product present");
//
//        Product getProduct = product.get();
//        log.info("ID: {}", getProduct.getId());
//        log.info("Name: {}", getProduct.getName());
//        log.info("Price: {}", getProduct.getPrice());
//        log.info("Quantity: {}", getProduct.getQuantity());
//        log.info("Created At: {}", getProduct.getCreatedAt());
//        log.info("Updated At: {}", getProduct.getUpdatedAt());
//    }
//
//    @Test
//    @Order(3)
//    void updateProduct() {
//        UpdateProductRequest updateProductRequest = UpdateProductRequest.builder()
//                .name("Test Update Repository")
//                .price(new BigDecimal("678912000"))
//                .quantity(123400)
//                .build();
//
//        // masukkan id yang sudah tersimpan
//        String id= "hp-pavilion-x360";
//        Optional<Product> productOptional = productRepository.findById(id);
//        assertTrue(productOptional.isPresent(), "Product present");
//
//        Product product = productOptional.get();
//        log.info("Name before update: {}", product.getName());
//        log.info("Price before update: {}", product.getPrice());
//        log.info("Quantity before update: {}", product.getQuantity());
//
//        product.setName(updateProductRequest.getName());
//        product.setPrice(updateProductRequest.getPrice());
//        product.setQuantity(updateProductRequest.getQuantity());
//        product.setUpdatedAt(LocalDateTime.now());
//
//        productRepository.save(product);
//        assertNotNull(product.getUpdatedAt());
//        log.info("Name after: {}", product.getName());
//        log.info("Price after: {}", product.getPrice());
//        log.info("Quantity after: {}", product.getQuantity());
//        log.info("Created At after: {}", product.getCreatedAt());
//        log.info("Updated At after: {}", product.getUpdatedAt());
//    }
//
//    @Test
//    @Order(4)
//    void deleteProduct() {
//        // masukkan id yang sudah tersimpan
//        String id= "hp-envy-x360";
//        Optional<Product> productOptional = productRepository.findById(id);
//        assertTrue(productOptional.isPresent(), "Product present");
//
//        Product product = productOptional.get();
//        productRepository.delete(product);
//
//        Optional<Product> productDeleted = productRepository.findById(id);
//        assertTrue(productDeleted.isEmpty(), "Product deleted");
//    }
//
//    @Test
//    @Order(5)
//    void getAllProduct() {
//        Iterable<Product> products = productRepository.findAll();
//        List<Product> productList = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
//        log.info("Size : {}", productList.size());
//    }
}