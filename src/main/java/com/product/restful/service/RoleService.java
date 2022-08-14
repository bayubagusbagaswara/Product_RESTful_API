package com.product.restful.service;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.role.RoleDto;
import com.product.restful.entity.user.UserPrincipal;

public interface RoleService {

    RoleDto createRole(RoleRequest roleRequest);

    MessageResponse deleteRole(Long id, UserPrincipal currentUser);
}
