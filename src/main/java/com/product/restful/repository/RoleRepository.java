package com.product.restful.repository;

import com.product.restful.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role r WHERE LOWER(r.name) = LOWER(:name)")
    Optional<Role> getByName(@RequestParam(name = "name") String name);
}
