package com.hauhh.services;

import com.hauhh.controllers.request.RoleCreationRequest;
import com.hauhh.controllers.request.RoleUpdateRequest;
import com.hauhh.controllers.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleCreationRequest request);


    RoleResponse updateRole(String id, RoleUpdateRequest request);


    void deleteRole(String id);


    List<RoleResponse> findAllRole();


    RoleResponse findRoleByID(String id);
}
