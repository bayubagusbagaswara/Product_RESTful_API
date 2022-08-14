package com.product.restful.service;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.role.CreateRoleRequest;
import com.product.restful.dto.role.RoleDTO;
import com.product.restful.entity.user.UserPrincipal;

public interface RoleService {

    RoleDTO createRole(CreateRoleRequest createRoleRequest);

    MessageResponse deleteRole(Long id, UserPrincipal currentUser);
}
