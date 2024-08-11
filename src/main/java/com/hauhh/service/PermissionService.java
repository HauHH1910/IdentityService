package com.hauhh.service;


import com.hauhh.dto.request.PermissionRequest;
import com.hauhh.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(String permission);
}
