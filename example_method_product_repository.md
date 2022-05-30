# Example Method for Product Repository

- findAll by keyword
```
    @Query("SELECT p FROM Product p WHERE "
            + "CONCAT(p.id, p.name, p.price, p.quantity)"
            + "LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);
```

```
    @Query("select u from User u where lower(u.name) like lower(concat('%', ?1, '%'))")
    List<User> findByNameFree(String name);
```

```
    @Query("select u from User u where lower(u.name) like lower(concat('%', :nameToFind, '%'))")
    List<User> findByNameFree(@Param("nameToFind") String name);
```

- Contains Example
```
    List<User> findByNameContaining(String name);
    List<User> findByNameContains(String name);
    List<User> findByNameIsContaining(String name);
```

- Case insensitivity example

```
    List<User> findByNameContainingIgnoreCase(String name);
```

- Or define as below

```
    @Query("select u from User u where lower(u.name) like lower(concat('%', ?name, '%'))")
    List<User> findByName(@Param("name") String name);
```

- If ignore case did not work

```
    @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
    Foo findByTitleRegex(String regexString);
```

- Limiting the result size with top and first

```
    User findFirstByOrderByLastnameAsc();
    User findTopByOrderByAgeDesc();
    Page<User> queryFirst10ByLastName(String lastname, Pageable pageable);
    Slice<User> findTop3ByLastname(String lastname, Pageable pageable);
    List<User> findFirst10ByLastname(String lastname, Sort sort);
    List<User> findTop10ByLastname(String lastname, Pageable pageable);
```
