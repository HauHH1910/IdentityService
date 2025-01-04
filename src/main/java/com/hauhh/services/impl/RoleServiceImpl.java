package com.hauhh.services.impl;

import com.hauhh.controllers.request.RoleRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.mappers.RoleMapper;
import com.hauhh.repositories.PermissionRepository;
import com.hauhh.repositories.RoleRepository;
import com.hauhh.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }
}
