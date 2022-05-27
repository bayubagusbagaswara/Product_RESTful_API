package com.product.restful.service.impl;

import com.product.restful.dto.product.*;
import com.product.restful.entity.Product;
import com.product.restful.exception.ProductNotFoundException;
import com.product.restful.repository.ProductRepository;
import com.product.restful.service.ProductService;
import com.product.restful.util.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;

    public ProductServiceImpl(ProductRepository productRepository, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        validationUtil.validate(createProductRequest);
        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setQuantity(createProductRequest.getQuantity());
        product.setDescription(createProductRequest.getDescription());
        product.setCreatedAt(Instant.now());
        productRepository.save(product);
        return mapProductToGetProductResponse(product);
    }

    @Override
    public ProductResponse getProductById(String productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        return mapProductToGetProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public ProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest) throws ProductNotFoundException {
        validationUtil.validate(updateProductRequest);
        Product product = getProduct(productId);
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());
        product.setUpdatedAt(Instant.now());
        productRepository.save(product);
        return mapProductToGetProductResponse(product);
    }

    @Override
    public void deleteProduct(String productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    @Override
    public ListProductResponse listAllProduct(ListProductRequest listProductRequest) {
        Integer pageNo = listProductRequest.getPageNo();
        Integer pageSize = listProductRequest.getPageSize();
        String sortBy = listProductRequest.getSortBy();
        String sortDir = listProductRequest.getSortDir();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> productList = products.getContent();

        List<ProductResponse> productRespons = mapProductListToGetProductResponseList(productList);

        ListProductResponse listAllProductResponse = new ListProductResponse();
        listAllProductResponse.setProductResponses(productRespons);
        listAllProductResponse.setPageNo(products.getNumber());
        listAllProductResponse.setPageSize(products.getSize());
        listAllProductResponse.setTotalElements(products.getTotalElements());
        listAllProductResponse.setTotalPages(products.getTotalPages());
        listAllProductResponse.setLast(products.isLast());
        return listAllProductResponse;
    }

    @Override
    public ProductResponse getProductByName(String name) throws ProductNotFoundException {
        Product product = productRepository.findByNameIgnoreCase(name).orElseThrow(() -> new ProductNotFoundException("Product Name = [" +name+ "] Not Found"));
        return mapProductToGetProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProductByNameContaining(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCase(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByNameStartingWith(String name) {
        List<Product> productList = productRepository.findByNameStartingWithIgnoreCase(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByNameContainingOrderByName(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByName(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByNameContainingOrderByNameDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByNameDesc(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseAndPriceBetween(name, priceMin, priceMax);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByNameContainingOrderByPrice(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPriceDesc(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByNameContainingOrderByPriceDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPrice(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByPriceBetween(priceMin, priceMax);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByPriceGreaterThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceGreaterThanEqual(price);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<ProductResponse> getProductByPriceLessThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceLessThanEqual(price);
        return mapProductListToGetProductResponseList(productList);
    }

    private List<ProductResponse> mapProductListToGetProductResponseList(List<Product> productList) {
        return productList.stream()
                .map((product) -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setId(product.getId());
                    productResponse.setName(product.getName());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setQuantity(product.getQuantity());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setCreatedBy(product.getCreatedBy());
                    productResponse.setCreatedAt(product.getCreatedAt());
                    productResponse.setUpdatedBy(product.getUpdatedBy());
                    productResponse.setUpdatedAt(product.getUpdatedAt());
                    return productResponse;
                })
                .collect(Collectors.toList())
                ;
    }

    private ProductResponse mapProductToGetProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setDescription(product.getDescription());
        productResponse.setCreatedBy(product.getCreatedBy());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedBy(product.getUpdatedBy());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }

    private Product getProduct(String id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product ID: [" +id+ "] not found"));
    }
}
