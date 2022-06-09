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
    public ResponseEntity<WebResponse<ProductResponse>> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        ProductResponse productResponse = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product was created successfully", productResponse), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ProductResponse>> getProductById(@PathVariable(name = "id") String id) {
        ProductResponse productResponse = productService.getProductById(id);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product successfully retrieved based on id", productResponse), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<WebResponse<ProductResponse>> updateProduct(@PathVariable(name = "id") String id, @RequestBody UpdateProductRequest updateProductRequest) {
        ProductResponse productResponse = productService.updateProduct(id, updateProductRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product updated successfully", productResponse), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<WebResponse<String>> deleteProduct(@PathVariable(name = "id") String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Product deleted successfully", null), HttpStatus.OK);
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

        ListProductResponse responses = productService.listAllProduct(listProductRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "All products successfully retrieved", responses), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ProductResponse>>> getProductByName(@PathVariable(name = "name") String name) {
        List<ProductResponse> productResponses = productService.getProductByNameContaining(name);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "All products successfully retrieved", productResponses), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}/price", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ProductResponse>>> getProductByNameAndPriceBetween(
            @PathVariable(name = "name") String name,
            @RequestParam(name = "priceMin") BigDecimal priceMin,
            @RequestParam(name = "priceMax") BigDecimal priceMax) {
        List<ProductResponse> productResponses = productService.getProductByNameContainingAndPriceBetween(name, priceMin, priceMax);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "All products successfully retrieved", productResponses), HttpStatus.OK);
    }
}
