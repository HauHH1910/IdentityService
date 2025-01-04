package com.hauhh.services;


import com.hauhh.controllers.request.PermissionRequest;
import com.hauhh.controllers.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(String permission);
}
