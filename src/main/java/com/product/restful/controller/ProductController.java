package com.product.restful.controller;

import com.product.restful.dto.CreateProductRequest;
import com.product.restful.dto.GetAllProductRequest;
import com.product.restful.dto.GetAllProductResponse;
import com.product.restful.dto.GetProductResponse;
import com.product.restful.dto.UpdateProductRequest;
import com.product.restful.dto.WebResponse;
import com.product.restful.exception.ProductNotFoundException;
import com.product.restful.service.ProductService;
import com.product.restful.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public WebResponse<GetProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        GetProductResponse getProductResponse = productService.createProduct(createProductRequest);
        return WebResponse.<GetProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .data(getProductResponse)
                .build();
    }

    @GetMapping(value = "/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<GetProductResponse> getProductById(@PathVariable("idProduct") String productId) throws ProductNotFoundException {
        GetProductResponse getProductResponse = productService.getProductById(productId);
        return WebResponse.<GetProductResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(getProductResponse)
                .build();
    }

    // UPDATE Product hanya bisa diakses oleh ROLE ADMIN or SUPER_ADMIN
    @PutMapping(value = "/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<GetProductResponse> updateProduct(@PathVariable("idProduct") String id, @RequestBody UpdateProductRequest updateProductRequest) throws ProductNotFoundException {
        GetProductResponse getProductResponse = productService.updateProduct(id, updateProductRequest);
        return WebResponse.<GetProductResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(getProductResponse)
                .build();
    }

    // DELETE Product hanya bisa diakses oleh ROLE ADMIN
    @DeleteMapping(value = "/{idProduct}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteProduct(@PathVariable("idProduct") String id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return WebResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(null)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<GetProductResponse>> getAllProduct() {
        List<GetProductResponse> getProductResponses = productService.getAllProduct();
        return WebResponse.<List<GetProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(getProductResponses)
                .build();
    }

    @GetMapping(value = "/listProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<GetAllProductResponse> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "keyword") String keyword) {

        GetAllProductRequest getAllProductRequest = new GetAllProductRequest();
        getAllProductRequest.setPageNo(pageNo);
        getAllProductRequest.setPageSize(pageSize);
        getAllProductRequest.setSortBy(sortBy);
        getAllProductRequest.setSortDir(sortDir);
        getAllProductRequest.setKeyword(keyword);
        GetAllProductResponse responses = productService.listAllProduct(getAllProductRequest);
        return new WebResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK, responses
        );
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<GetProductResponse>> getProductByName(@PathVariable("name") String name) {
        List<GetProductResponse> getProductResponses = productService.getProductByNameContaining(name);
        return WebResponse.<List<GetProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(getProductResponses)
                .build();
    }

    @GetMapping(value = "/price/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<GetProductResponse>> getProductByNameAndPriceBetween(
            @PathVariable("name") String name,
            @RequestParam(value = "priceMin") BigDecimal priceMin,
            @RequestParam(value = "priceMax") BigDecimal priceMax) {
        List<GetProductResponse> getProductResponses = productService.getProductByNameContainingAndPriceBetween(name, priceMin, priceMax);
        return WebResponse.<List<GetProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(getProductResponses)
                .build();
    }
}
