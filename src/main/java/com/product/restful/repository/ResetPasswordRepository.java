package com.product.restful.repository;

import com.product.restful.entity.user.ResetPassword;
import com.product.restful.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPassword, Long> {

    Optional<ResetPassword> findByUniqueCode(String uniqueCode);

    void deleteByUser(User user);
}
