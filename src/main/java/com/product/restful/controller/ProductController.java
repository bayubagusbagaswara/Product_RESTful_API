package com.product.restful.controller;

import com.product.restful.dto.product.CreateProductRequest;
import com.product.restful.dto.product.ListProductRequest;
import com.product.restful.dto.product.ListProductResponse;
import com.product.restful.dto.product.ProductResponse;
import com.product.restful.dto.product.UpdateProductRequest;
import com.product.restful.dto.WebResponse;
import com.product.restful.service.ProductService;
import com.product.restful.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MEMBER')")
    public WebResponse<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        ProductResponse productResponse = productService.createProduct(createProductRequest);
        return WebResponse.<ProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .data(productResponse)
                .build();
    }

    @GetMapping(value = "/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProductResponse> getProductById(@PathVariable("idProduct") String productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return WebResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(productResponse)
                .build();
    }

    @PutMapping(value = "/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<ProductResponse> updateProduct(@PathVariable("idProduct") String id, @RequestBody UpdateProductRequest updateProductRequest) {
        ProductResponse productResponse = productService.updateProduct(id, updateProductRequest);
        return WebResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(productResponse)
                .build();
    }

    @DeleteMapping(value = "/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> deleteProduct(@PathVariable("idProduct") String id) {
        productService.deleteProduct(id);
        return WebResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(null)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> productRespons = productService.getAllProduct();
        return WebResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(productRespons)
                .build();
    }

    @GetMapping(value = "/listProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ListProductResponse> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        ListProductRequest listProductRequest = new ListProductRequest();
        listProductRequest.setPageNo(pageNo);
        listProductRequest.setPageSize(pageSize);
        listProductRequest.setSortBy(sortBy);
        listProductRequest.setSortDir(sortDir);
        ListProductResponse responses = productService.listAllProduct(listProductRequest);
        return new WebResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK, responses
        );
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getProductByName(@PathVariable("name") String name) {
        List<ProductResponse> productRespons = productService.getProductByNameContaining(name);
        return WebResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(productRespons)
                .build();
    }

    @GetMapping(value = "/price/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getProductByNameAndPriceBetween(
            @PathVariable("name") String name,
            @RequestParam(value = "priceMin") BigDecimal priceMin,
            @RequestParam(value = "priceMax") BigDecimal priceMax) {
        List<ProductResponse> productRespons = productService.getProductByNameContainingAndPriceBetween(name, priceMin, priceMax);
        return WebResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(productRespons)
                .build();
    }
}
