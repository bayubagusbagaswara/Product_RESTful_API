package com.product.restful.service.impl;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class ProductServiceImplMockitoTest {
//
//    private final static Logger log = LoggerFactory.getLogger(ProductServiceImplMockitoTest.class);
//
//    @Mock
//    ProductRepository productRepository;
//
//    @InjectMocks
//    ProductServiceImpl productService;
//
//    @Test
//    void createProductTestMock() {
//        CreateProductRequest createProductRequest = CreateProductRequest.builder()
//                .name("Test Service Mockito")
//                .price(new BigDecimal("12345000"))
//                .quantity(345)
//                .description("This is test service mockito product description")
//                .build();
//        Product product = Product.builder()
//                .name(createProductRequest.getName())
//                .price(createProductRequest.getPrice())
//                .quantity(createProductRequest.getQuantity())
//                .description(createProductRequest.getDescription())
//                .build();
//
//        Mockito
//                .when(productRepository.save(product))
//                .thenReturn(product);
//
//        GetProductResponse productResponse = productService.createProduct(createProductRequest);
//        assertSame(createProductRequest.getName(), productResponse.getName());
//        assertSame(createProductRequest.getPrice(), productResponse.getPrice());
//        assertSame(createProductRequest.getQuantity(), productResponse.getQuantity());
//        assertSame(createProductRequest.getDescription(), productResponse.getDescription());
//        assertNotNull(productResponse.getCreatedAt());
//
//        log.info("Id: {}", productResponse.getId());
//        log.info("Name: {}", productResponse.getName());
//        log.info("Price: {}", productResponse.getPrice());
//        log.info("Quantity: {}", productResponse.getQuantity());
//        log.info("Description: {}", productResponse.getDescription());
//        log.info("Created At: {}", productResponse.getCreatedAt());
//        log.info("Created By: {}", productResponse.getCreatedBy());
//    }
}