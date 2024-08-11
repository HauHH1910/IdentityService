package com.hauhh.controller;


import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.PermissionRequest;
import com.hauhh.dto.response.PermissionResponse;
import com.hauhh.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @GetMapping
    public ResponseData<List<PermissionResponse>> getAllPermissions() {
        return ResponseData.<List<PermissionResponse>>builder()
                .message("Get all permissions")
                .result(permissionService.getAllPermissions())
                .build();
    }

    @PostMapping
    public ResponseData<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
        return ResponseData.<PermissionResponse>builder()
                .message("Create permission")
                .result(permissionService.createPermission(request))
                .build();
    }

    @DeleteMapping("/{permission}")
    public ResponseData<Void> deletePermission(@PathVariable("permission") String permission){
        permissionService.deletePermission(permission);
        return ResponseData.<Void>builder()
                .message("Delete permission")
                .build();
    }

}
