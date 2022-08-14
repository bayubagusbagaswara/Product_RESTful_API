package com.product.restful.service.impl;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.role.RoleDto;
import com.product.restful.entity.Role;
import com.product.restful.entity.enumerator.RoleName;
import com.product.restful.entity.user.UserPrincipal;
import com.product.restful.exception.AccessDeniedException;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.repository.RoleRepository;
import com.product.restful.service.RoleService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(RoleName.valueOf(roleRequest.getName().toUpperCase()));
        role.setCreatedAt(Instant.now());
        roleRepository.save(role);
        return RoleDto.fromEntity(role);
    }

    @Override
    public MessageResponse deleteRole(Long id, UserPrincipal currentUser) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (!currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ADMIN.toString()))) {
            throw new AccessDeniedException(new MessageResponse(Boolean.FALSE, "You don't have permission to delete role of: " + role.getName()));
        }

        roleRepository.delete(role);
        return new MessageResponse(Boolean.TRUE, "You successfully deleted role id of: " + id);
    }
}
