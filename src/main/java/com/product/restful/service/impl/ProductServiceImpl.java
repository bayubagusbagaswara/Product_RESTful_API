package com.product.restful.service.impl;

import com.product.restful.dto.*;
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
import java.time.LocalDateTime;
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
    public GetProductResponse createProduct(CreateProductRequest createProductRequest) {
        validationUtil.validate(createProductRequest);
        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        product.setQuantity(createProductRequest.getQuantity());
        product.setDescription(createProductRequest.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
        return mapProductToGetProductResponse(product);
    }

    @Override
    public GetProductResponse getProductById(String productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        return mapProductToGetProductResponse(product);
    }

    @Override
    public List<GetProductResponse> getAllProduct() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public GetProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest) throws ProductNotFoundException {
        validationUtil.validate(updateProductRequest);
        Product product = getProduct(productId);
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return mapProductToGetProductResponse(product);
    }

    @Override
    public void deleteProduct(String productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    @Override
    public GetAllProductResponse listAllProduct(GetAllProductRequest getAllProductRequest) {
        Integer pageNo = getAllProductRequest.getPageNo();
        Integer pageSize = getAllProductRequest.getPageSize();
        String sortBy = getAllProductRequest.getSortBy();
        String sortDir = getAllProductRequest.getSortDir(); // asc or desc

        // Sorting, cek apakah sortDir = ASC, jika ya maka urutkan secara ascending, jika tidak maka urutkan secara descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // masukkan pageable sebagai parameter method findAll
        Page<Product> products = productRepository.findAll(pageable);

        // ambil data hasil findAll
        List<Product> productList = products.getContent();

        // mapping menjadi GetProductResponse
        List<GetProductResponse> getProductResponses = mapProductListToGetProductResponseList(productList);

        // buat object ListAllProductResponse, dan masukkan list data product
        // dan disini kita bisa ambil data Size, Total Data, Total Pages, dan Page saat ini
        GetAllProductResponse listAllProductResponse = new GetAllProductResponse();
        listAllProductResponse.setGetProductResponses(getProductResponses);
        listAllProductResponse.setPageNo(products.getNumber());
        listAllProductResponse.setPageSize(products.getSize());
        listAllProductResponse.setTotalElements(products.getTotalElements());
        listAllProductResponse.setTotalPages(products.getTotalPages());
        listAllProductResponse.setLast(products.isLast());
        return listAllProductResponse;
    }

    @Override
    public GetProductResponse getProductByName(String name) throws ProductNotFoundException {
        Product product = productRepository.findByNameIgnoreCase(name).orElseThrow(() -> new ProductNotFoundException("Product Name = [" +name+ "] Not Found"));
        return mapProductToGetProductResponse(product);
    }

    @Override
    public List<GetProductResponse> getProductByNameContaining(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCase(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByNameStartingWith(String name) {
        List<Product> productList = productRepository.findByNameStartingWithIgnoreCase(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByNameContainingOrderByName(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByName(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByNameContainingOrderByNameDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByNameDesc(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByNameContainingAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseAndPriceBetween(name, priceMin, priceMax);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByNameContainingOrderByPrice(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPriceDesc(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByNameContainingOrderByPriceDesc(String name) {
        List<Product> productList = productRepository.findByNameContainsIgnoreCaseOrderByPrice(name);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByPriceBetween(BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> productList = productRepository.findByPriceBetween(priceMin, priceMax);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByPriceGreaterThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceGreaterThanEqual(price);
        return mapProductListToGetProductResponseList(productList);
    }

    @Override
    public List<GetProductResponse> getProductByPriceLessThanEqual(BigDecimal price) {
        List<Product> productList = productRepository.findByPriceLessThanEqual(price);
        return mapProductListToGetProductResponseList(productList);
    }

    private List<GetProductResponse> mapProductListToGetProductResponseList(List<Product> productList) {
        return productList.stream()
                .map((product) -> {
                    GetProductResponse getProductResponse = new GetProductResponse();
                    getProductResponse.setId(product.getId());
                    getProductResponse.setName(product.getName());
                    getProductResponse.setPrice(product.getPrice());
                    getProductResponse.setQuantity(product.getQuantity());
                    getProductResponse.setDescription(product.getDescription());
                    getProductResponse.setCreatedBy(product.getCreatedBy());
                    getProductResponse.setCreatedAt(product.getCreatedAt());
                    getProductResponse.setUpdatedBy(product.getUpdatedBy());
                    getProductResponse.setUpdatedAt(product.getUpdatedAt());
                    return getProductResponse;
                })
                .collect(Collectors.toList())
                ;
    }

    private GetProductResponse mapProductToGetProductResponse(Product product) {
        GetProductResponse getProductResponse = new GetProductResponse();
        getProductResponse.setId(product.getId());
        getProductResponse.setName(product.getName());
        getProductResponse.setPrice(product.getPrice());
        getProductResponse.setQuantity(product.getQuantity());
        getProductResponse.setDescription(product.getDescription());
        getProductResponse.setCreatedBy(product.getCreatedBy());
        getProductResponse.setCreatedAt(product.getCreatedAt());
        getProductResponse.setUpdatedBy(product.getUpdatedBy());
        getProductResponse.setUpdatedAt(product.getUpdatedAt());
        return getProductResponse;
    }

    private Product getProduct(String id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product ID: [" +id+ "] not found"));
    }
}
