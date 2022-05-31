package com.product.restful.dto.role;

import com.product.restful.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private Long id;

    private String name;

    private Instant createdAt;

    public static RoleResponse from(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName().getRoleName());
        response.setCreatedAt(role.getCreatedAt());
        return response;
    }
}
