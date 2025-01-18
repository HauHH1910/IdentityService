package com.hauhh.controllers;


import com.hauhh.commons.ResponseData;
import com.hauhh.configurations.Translator;
import com.hauhh.controllers.request.RoleCreationRequest;
import com.hauhh.controllers.request.RoleUpdateRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Role Controller")
@Slf4j(topic = "Role Controller")
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseData<RoleResponse> create(@RequestBody @Valid RoleCreationRequest request) {
        log.info("[CREATE]");
        return ResponseData.<RoleResponse>builder()
                .code(1000)
                .message(Translator.toLocale("role.created"))
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ResponseData<List<RoleResponse>> getAll() {
        log.info("[Get all roles]");
        return ResponseData.<List<RoleResponse>>builder()
                .message(Translator.toLocale("role.get.all"))
                .result(roleService.findAllRole())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<RoleResponse> getRoleById(@PathVariable String id) {
        return ResponseData.<RoleResponse>builder()
                .message(Translator.toLocale("role.get"))
                .result(roleService.findRoleByID(id))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<RoleResponse> update(@PathVariable String id, @RequestBody RoleUpdateRequest request) {
        return ResponseData.<RoleResponse>builder()
                .message(Translator.toLocale("role.update"))
                .result(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseData.<Void>builder()
                .message(Translator.toLocale("role.delete"))
                .build();
    }
}
