package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.role.RoleRequest;
import com.product.restful.dto.role.RoleResponse;
import com.product.restful.entity.UserPrincipal;
import com.product.restful.security.CurrentUser;
import com.product.restful.service.RoleService;
import org.apache.catalina.WebResource;
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
    public ResponseEntity<WebResponse<RoleResponse>> createRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.createRole(roleRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Role was created successfully", roleResponse), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = roleService.deleteRole(id, currentUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
