package com.product.restful.service.impl;

import com.product.restful.dto.role.CreateRoleRequest;
import com.product.restful.dto.role.RoleDTO;
import com.product.restful.entity.Role;
import com.product.restful.entity.enumerator.RoleName;
import com.product.restful.exception.ResourceNotFoundException;
import com.product.restful.mapper.RoleMapper;
import com.product.restful.repository.RoleRepository;
import com.product.restful.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO createRole(CreateRoleRequest createRoleRequest) {
        Role role = new Role();
        role.setName(RoleName.valueOf(createRoleRequest.getName().toUpperCase()));
        roleRepository.save(role);
        return roleMapper.mapFromRole(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(role);
    }
}
