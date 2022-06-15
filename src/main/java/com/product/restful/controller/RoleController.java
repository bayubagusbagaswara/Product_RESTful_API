package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.role.RoleDto;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RoleDto>> createRole(@RequestBody RoleRequest roleRequest) {
        RoleDto roleDto = roleService.createRole(roleRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Role was created successfully", roleDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable(name = "id") Long id, UserPrincipal currentUser) {
        ApiResponse apiResponse = roleService.deleteRole(id, currentUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
