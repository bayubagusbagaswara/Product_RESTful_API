package com.product.restful.repository;

import com.product.restful.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findByNameContainsIgnoreCase(String name);
    List<Product> findByNameStartingWithIgnoreCase(String name);
    List<Product> findByNameContainsIgnoreCaseOrderByName(String name);
    List<Product> findByNameContainsIgnoreCaseOrderByNameDesc(String name);

    // NAME DAN PRICE
    List<Product> findByNameContainsIgnoreCaseAndPriceBetween(String name, BigDecimal priceMin, BigDecimal priceMax);
    List<Product> findByNameContainsIgnoreCaseOrderByPrice(String name);
    List<Product> findByNameContainsIgnoreCaseOrderByPriceDesc(String name);

    // PRICE
    List<Product> findByPriceBetween(BigDecimal priceMin, BigDecimal priceMax);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);

//    @Query("select u from User u where lower(u.name) like lower(concat('%', ?1, '%'))")
//    List<User> findByNameFree(String name);

//    @Query("select u from User u where lower(u.name) like lower(concat('%', :nameToFind, '%'))")
//    List<User> findByNameFree(@Param("nameToFind") String name);

    // contains example
//    List<User> findByNameContaining(String name);
//    List<User> findByNameContains(String name);
//    List<User> findByNameIsContaining(String name);

    // case insensitivity example
//    List<User> findByNameContainingIgnoreCase(String name);

    // or define as below
//    @Query("select u from User u where lower(u.name) like lower(concat('%', ?name, '%'))")
//    List<User> findByName(@Param("name") String name);

    // if ignore case did not work
//    @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
//    Foo findByTitleRegex(String regexString);

    // limiting the result size with top and first
//    User findFirstByOrderByLastnameAsc();
//    User findTopByOrderByAgeDesc();
//    Page<User> queryFirst10ByLastName(String lastname, Pageable pageable);
//    Slice<User> findTop3ByLastname(String lastname, Pageable pageable);
//    List<User> findFirst10ByLastname(String lastname, Sort sort);
//    List<User> findTop10ByLastname(String lastname, Pageable pageable);

}
