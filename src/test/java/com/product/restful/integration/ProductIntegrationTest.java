package com.product.restful.integration;

import com.product.restful.dto.product.CreateProductRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static io.restassured.RestAssured.port;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.with;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductIntegrationTest {

    @LocalServerPort
    private Integer serverPort;

    @BeforeEach
    void setUp() {
        baseURI = "http://localhost";
        port = serverPort;
        System.out.println("Port: " + serverPort);
    }

    @Test
    @Order(1)
    void createProduct() {
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .name("Test Service")
                .price(new BigDecimal("12345000"))
                .quantity(345)
                .build();
        with()
                .body(createProductRequest)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/products")
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
    }

//    @Test
//    @Order(2)
//    void getProductById() {
//        String id = "macbook-pro-14-2021";
//        with()
//                .param(id)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/api/products/"+id)
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                ;
//    }
//
//    @Test
//    @Order(3)
//    void updateProduct() {
//        String id = "hp-pavilion-x360";
//        UpdateProductRequest updateProductRequest = UpdateProductRequest.builder()
//                .name("Test Update Integration")
//                .price(new BigDecimal("98760000"))
//                .quantity(12345)
//                .build();
//        with()
//                .param(id)
//                .body(updateProductRequest)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .put("/api/products/"+id)
//                .then()
//                .statusCode(HttpStatus.OK.value())
//        ;
//    }
//
//    @Test
//    @Order(4)
//    void deleteProduct() {
//        String id = "hp-envy-x360";
//        with()
//                .param(id)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .delete("/api/products/"+id)
//                .then()
//                .statusCode(HttpStatus.OK.value())
//        ;
//    }
//
//    @Test
//    @Order(5)
//    void getAllProduct() {
//        with()
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/api/products")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//        ;
//    }
}
