package com.product.restful.service.impl;

import com.product.restful.dto.product.*;
import com.product.restful.entity.Product;
import com.product.restful.repository.ProductRepository;
import com.product.restful.service.ProductService;
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

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(CreateProductRequest createProductRequest) {

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setQuantity(createProductRequest.getQuantity());
        product.setDescription(createProductRequest.getDescription());
        product.setCreatedAt(Instant.now());

        productRepository.save(product);
        return ProductDto.fromEntity(product);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = productRepository.getProductById(id);
        return ProductDto.fromEntity(product);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public ProductDto updateProduct(String id, UpdateProductRequest updateProductRequest) {

        Product product = productRepository.getProductById(id);
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());
        product.setUpdatedAt(Instant.now());

        productRepository.save(product);
        return ProductDto.fromEntity(product);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = productRepository.getProductById(id);
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

        List<ProductDto> productRespons = ProductDto.fromEntityList(productList);

        ListProductResponse listAllProductResponse = new ListProductResponse();
        listAllProductResponse.setProducts(productRespons);
        listAllProductResponse.setPageNo(products.getNumber());
        listAllProductResponse.setPageSize(products.getSize());
        listAllProductResponse.setTotalElements(products.getTotalElements());
        listAllProductResponse.setTotalPages(products.getTotalPages());
        listAllProductResponse.setLast(products.isLast());
        return listAllProductResponse;
    }

    @Override
    public ProductDto getProductByName(String name) {
        Product product = productRepository.getProductByName(name);
        return ProductDto.fromEntity(product);
    }

    @Override
    public List<ProductDto> getProductByNameContaining(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCase(name);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByNameStartingWith(String name) {
        List<Product> productList = productRepository.findByNameStartingWithIgnoreCase(name);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByNameContainingOrderByName(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByName(name);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByNameContainingOrderByNameDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByNameDesc(name);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseAndPriceBetween(name, priceMin, priceMax);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByNameContainingOrderByPrice(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPriceDesc(name);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByNameContainingOrderByPriceDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPrice(name);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByPriceBetween(priceMin, priceMax);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByPriceGreaterThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceGreaterThanEqual(price);
        return ProductDto.fromEntityList(productList);
    }

    @Override
    public List<ProductDto> getProductByPriceLessThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceLessThanEqual(price);
        return ProductDto.fromEntityList(productList);
    }

}
