package com.product.restful.dto.role;

import com.product.restful.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;

    public static RoleDTO mapFromEntity(Role role) {
        return new RoleDTO(role.getId(), role.getName().name());
    }

    public static Set<RoleDTO> mapFromEntitiesSet(Set<Role> roles) {
        return roles.stream()
                .map(RoleDTO::mapFromEntity)
                .collect(Collectors.toSet());
    }
}
