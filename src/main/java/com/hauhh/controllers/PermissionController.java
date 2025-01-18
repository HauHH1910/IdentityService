package com.hauhh.controllers;


import com.hauhh.commons.ResponseData;
import com.hauhh.configurations.Translator;
import com.hauhh.controllers.request.PermissionRequest;
import com.hauhh.controllers.response.PermissionResponse;
import com.hauhh.services.PermissionService;
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
@Tag(name = "Permission Controller")
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @Operation(
            summary = "Get all permissions",
            description = "Get all permissions"
    )
    @GetMapping
    public ResponseData<List<PermissionResponse>> getAllPermissions() {
        return ResponseData.<List<PermissionResponse>>builder()
                .message(Translator.toLocale("permission.get.all"))
                .result(permissionService.getAllPermissions())
                .build();
    }

    @Operation(
            summary = "Create permissions",
            description = "Create permissions for user"
    )
    @PostMapping
    public ResponseData<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ResponseData.<PermissionResponse>builder()
                .message(Translator.toLocale("permission.create"))
                .result(permissionService.createPermission(request))
                .build();
    }

    @Operation(
            summary = "Delete permissions",
            description = "Delete permissions of users"
    )
    @DeleteMapping("/{permission}")
    public ResponseData<Void> deletePermission(@PathVariable("permission") String permission) {
        permissionService.deletePermission(permission);
        return ResponseData.<Void>builder()
                .message(Translator.toLocale("permission.delete"))
                .build();
    }

}
