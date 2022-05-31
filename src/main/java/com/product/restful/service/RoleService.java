package com.product.restful.service;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.role.RoleResponse;
import com.product.restful.entity.UserPrincipal;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);

    ApiResponse deleteRole(Long id, UserPrincipal currentUser);
}
