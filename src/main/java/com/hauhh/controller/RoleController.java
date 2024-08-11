package com.hauhh.controller;


import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.RoleRequest;
import com.hauhh.dto.response.RoleResponse;
import com.hauhh.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ResponseData<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ResponseData.<RoleResponse>builder()
                .message("Create Role")
                .result(roleService.createRole(roleRequest))
                .build();
    }

    @GetMapping
    public ResponseData<List<RoleResponse>> getAllRoles() {
        return ResponseData.<List<RoleResponse>>builder()
                .message("Get All Roles")
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/{role}")
    public ResponseData<Void> deleteRole(@PathVariable String role) {
        roleService.deleteRole(role);
        return ResponseData.<Void>builder()
                .message("Delete Role")
                .build();
    }

}
