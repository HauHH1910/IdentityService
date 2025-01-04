package com.hauhh.services;

import com.hauhh.controllers.request.RoleRequest;
import com.hauhh.controllers.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    void deleteRole(String role);
}
