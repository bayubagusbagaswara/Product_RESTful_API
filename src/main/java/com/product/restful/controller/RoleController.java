package com.product.restful.controller;

import com.product.restful.dto.MessageResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.role.CreateRoleRequest;
import com.product.restful.dto.role.RoleDTO;
import com.product.restful.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RoleDTO>> createRole(@RequestBody CreateRoleRequest createRoleRequest) {
        RoleDTO roleDto = roleService.createRole(createRoleRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Role was created successfully", roleDto), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteRole(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(new MessageResponse(Boolean.TRUE, "You successfully deleted role id of: " + id), HttpStatus.OK);
    }
}
