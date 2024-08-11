package com.hauhh.service;

import com.hauhh.dto.request.RoleRequest;
import com.hauhh.dto.response.RoleResponse;
import com.hauhh.entity.Role;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    void deleteRole(String role);
}
