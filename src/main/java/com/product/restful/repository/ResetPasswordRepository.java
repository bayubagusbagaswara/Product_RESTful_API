package com.product.restful.repository;

import com.product.restful.entity.user.ResetPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPassword, Long> {
}
