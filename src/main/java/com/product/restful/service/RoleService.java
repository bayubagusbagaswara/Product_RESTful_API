package com.product.restful.service;

import com.product.restful.dto.role.CreateRoleRequest;
import com.product.restful.dto.role.RoleDTO;

public interface RoleService {

    RoleDTO createRole(CreateRoleRequest createRoleRequest);

    void deleteRole(Long id);
}
