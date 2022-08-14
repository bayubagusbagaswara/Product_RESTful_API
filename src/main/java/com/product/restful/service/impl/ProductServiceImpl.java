package com.product.restful.service.impl;

import com.product.restful.dto.product.*;
import com.product.restful.entity.Product;
import com.product.restful.exception.ResourceNotFoundException;
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
    public ProductDTO createProduct(CreateProductRequest createProductRequest) {

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setQuantity(createProductRequest.getQuantity());
        product.setDescription(createProductRequest.getDescription());
        product.setCreatedAt(Instant.now());

        productRepository.save(product);
        return ProductDTO.mapFromEntity(product);
    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.getProductById(id);
        return ProductDTO.mapFromEntity(product);
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
        return ProductDTO.mapFromEntityList(productList);
    }

    @Override
    public ProductDTO updateProduct(String id, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.getProductById(id);
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());
        productRepository.save(product);
        return ProductDTO.mapFromEntity(product);
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
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> productList = productPage.getContent();
        List<ProductDTO> productDTOS = ProductDTO.mapFromEntityList(productList);

        return ListProductResponse.builder()
                .products(productDTOS)
                .pageNo(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    @Override
    public ProductDTO getProductByName(String name) {
        Product product = productRepository.getProductByName(name);
        return ProductDTO.mapFromEntity(product);
    }

    @Override
    public List<ProductDTO> getProductByNameContaining(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCase(name);
        return ProductDTO.mapFromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByNameStartingWith(String name) {
        List<Product> productList = productRepository.findByNameStartingWithIgnoreCase(name);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByNameContainingOrderByName(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByName(name);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByNameContainingOrderByNameDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByNameDesc(name);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseAndPriceBetween(name, priceMin, priceMax);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByNameContainingOrderByPrice(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPriceDesc(name);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByNameContainingOrderByPriceDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPrice(name);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByPriceBetween(priceMin, priceMax);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByPriceGreaterThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceGreaterThanEqual(price);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public List<ProductDTO> getProductByPriceLessThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceLessThanEqual(price);
        return ProductDTO.fromEntityList(productList);
    }

    @Override
    public Product getProductByIdNeW(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }
}
