package com.product.restful.repository;

import com.product.restful.entity.Role;
import com.product.restful.entity.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void testCreateRoles() {
        Instant dateNow = Instant.now();

        Role manager = new Role(RoleName.MANAGER);
        manager.setCreatedAt(dateNow);
        manager.setUpdatedAt(dateNow);

        Role admin = new Role(RoleName.ADMIN);
        admin.setCreatedAt(dateNow);
        admin.setUpdatedAt(dateNow);

        Role customer = new Role(RoleName.CUSTOMER);
        customer.setCreatedAt(dateNow);
        customer.setUpdatedAt(dateNow);

        Role user = new Role(RoleName.USER);
        user.setCreatedAt(dateNow);
        user.setUpdatedAt(dateNow);

        roleRepository.saveAll(List.of(manager, admin, customer, user));
        long count = roleRepository.count();
        assertEquals(4, count);
    }
}