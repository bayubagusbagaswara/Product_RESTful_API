package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.product.*;
import com.product.restful.dto.product.ProductDto;
import com.product.restful.dto.WebResponse;
import com.product.restful.entity.Product;
import com.product.restful.service.ProductService;
import com.product.restful.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import javax.validation.Valid;
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
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        ProductDto product = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, String.format("Product name %s was created successfully", product.getName())), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ProductDto>> getProductById(@PathVariable(name = "id") String id) {
        ProductDto product = productService.getProductById(id);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product successfully retrieved based on id", product), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<WebResponse<ProductDto>> updateProduct(@PathVariable(name = "id") String id, @RequestBody UpdateProductRequest updateProductRequest) {
        ProductDto product = productService.updateProduct(id, updateProductRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product updated successfully", product), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(name = "id") String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, String.format("Product id %s deleted successfully", id)), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ListProductResponse>> getAllProducts(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        ListProductRequest listProductRequest = ListProductRequest.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        ListProductResponse allProducts = productService.listAllProduct(listProductRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "All products successfully retrieved", allProducts), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ProductDto>>> getProductByName(@PathVariable(name = "name") String name) {
        List<ProductDto> products = productService.getProductByNameContaining(name);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "All products successfully retrieved", products), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}/price", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ProductDto>>> getProductByNameAndPriceBetween(
            @PathVariable(name = "name") String name,
            @RequestParam(name = "priceMin") BigDecimal priceMin,
            @RequestParam(name = "priceMax") BigDecimal priceMax) {
        List<ProductDto> products = productService.getProductByNameContainingAndPriceBetween(name, priceMin, priceMax);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "All products successfully retrieved", products), HttpStatus.OK);
    }

    @GetMapping(value = "/new/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Product>> getProductByIdNew(@PathVariable(name = "id") String id) {
        Product product = productService.getProductByIdNeW(id);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product successfully retrieved based on id", product), HttpStatus.OK);
    }

}
