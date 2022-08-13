package com.product.restful.repository;

import com.product.restful.entity.user.UserPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends CrudRepository<UserPassword, Long> {

    UserPassword findByUserId(Long userId);
}
