package com.product.restful.repository;

import com.product.restful.entity.Product;
import com.product.restful.exception.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @NonNull
    @Override
    Optional<Product> findById(@NonNull String id);

    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findByNameContainsIgnoreCase(String name);
    List<Product> findByNameStartingWithIgnoreCase(String name);
    List<Product> findByNameContainsIgnoreCaseOrderByName(String name);
    List<Product> findByNameContainsIgnoreCaseOrderByNameDesc(String name);

    List<Product> findByNameContainsIgnoreCaseAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax);
    List<Product> findByNameContainsIgnoreCaseOrderByPrice(String name);
    List<Product> findByNameContainsIgnoreCaseOrderByPriceDesc(String name);

    List<Product> findByPriceBetween(BigDecimal priceMin, BigDecimal priceMax);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);


    default Product getProductById(String id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    default Product getProductByName(String name) {
        return findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
    }

}
