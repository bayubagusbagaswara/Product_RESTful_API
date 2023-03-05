package com.product.restful.repository;

import com.product.restful.entity.Product;
import com.product.restful.exception.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    // ambil semua product dengan minimum price
    @Query(value = "SELECT * FROM Product p WHERE p.price > :minPrice", nativeQuery = true)
    List<Product> getProductsWithMinimumPrice(BigDecimal minPrice);
    @Query(value = "SELECT * FROM Product p WHERE p.price < :maxPrice", nativeQuery = true,
    countQuery = "SELECT * FROM Product p WHERE p.price < :maxPrice")
    List<Product> getProductsWithMaximumPrice(BigDecimal maxPrice);

    // misal kita update amount pada table product
    @Query(value = "UPDATE Product p SET p.price = p.prince + :amount", nativeQuery = true)
    void updateAmount(Integer amount);

    // ambil semua product dimana enabled bernilai true
    // dan category ID OR category allParent IDs
    // lalu urutkan berdasarkan name secara ascending
    @Query(value = "SELECT p FROM Product p WHERE p.enabled = true " +
            "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%) " +
            "ORDER BY p.name ASC", nativeQuery = true)
    Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);

    @Query(value = "SELECT * FROM Product p WHERE enabled = true " +
            "AND MATCH(name, short_description, full_description) AGAINST (?1)", nativeQuery = true)
    Page<Product> search(String keyword, Pageable pageable);

    @Query("UPDATE Product p SET p.averageRating = " +
            "COALESCE((SELECT AVG(r.rating) FROM Review r WHERE " +
            "p.reviewCount = (SELECT COUNT(r.id) FROM Review r WHERE r.product.id = ?1)" +
            "WHERE p.id = ?1")
    void updateReviewCountAndAverageRating(Integer productId);

    default Product getProductById(String id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    default Product getProductByName(String name) {
        return findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
    }

}
