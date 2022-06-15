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
public class RoleDto {

    private Long id;

    private String name;

    public static RoleDto from(Role role) {
        return new RoleDto(role.getId(), role.getName().name());
    }

    public static Set<RoleDto> fromRoles(Set<Role> roles) {
        return roles.stream()
                .map(RoleDto::from)
                .collect(Collectors.toSet());
    }

}
