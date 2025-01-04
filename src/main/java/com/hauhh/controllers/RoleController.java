package com.hauhh.controllers;


import com.hauhh.commons.ResponseData;
import com.hauhh.controllers.request.RoleRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Role Controller")
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @Operation(
            summary = "Create new Role",
            description = "Create new Role for User"
    )
    @PostMapping
    public ResponseData<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ResponseData.<RoleResponse>builder()
                .message("Create Role")
                .result(roleService.createRole(roleRequest))
                .build();
    }

    @Operation(
            summary = "Get all roles",
            description = "Get all roles from database"
    )
    @GetMapping
    public ResponseData<List<RoleResponse>> getAllRoles() {
        return ResponseData.<List<RoleResponse>>builder()
                .message("Get All Roles")
                .result(roleService.getAllRoles())
                .build();
    }


    @Operation(
            summary = "Delete role",
            description = "Delete role"
    )
    @DeleteMapping("/{role}")
    public ResponseData<Void> deleteRole(@PathVariable String role) {
        roleService.deleteRole(role);
        return ResponseData.<Void>builder()
                .message("Delete Role")
                .build();
    }

}
