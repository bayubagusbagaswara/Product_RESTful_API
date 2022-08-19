package com.product.restful.mapper;

import com.product.restful.dto.role.RoleDTO;
import com.product.restful.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleDTO mapFromRole(Role role) {
        return new RoleDTO(role.getId(), role.getName().name());
    }

    public Set<RoleDTO> mapFromRoleSet(Set<Role> roles) {
        return roles.stream()
                .map(this::mapFromRole)
                .collect(Collectors.toSet());
    }
}
