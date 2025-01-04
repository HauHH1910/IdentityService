package com.hauhh.services.impl;

import com.hauhh.controllers.request.RoleCreationRequest;
import com.hauhh.controllers.request.RoleUpdateRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.exceptions.AppException;
import com.hauhh.mappers.RoleMapper;
import com.hauhh.models.User;
import com.hauhh.models.enums.ErrorCode;
import com.hauhh.models.enums.Role;
import com.hauhh.repositories.PermissionRepository;
import com.hauhh.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<
        RoleResponse,
        RoleCreationRequest,
        RoleUpdateRequest
> {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponse create(RoleCreationRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse update(String id, RoleUpdateRequest request) {
        var role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleMapper.updateRole(role, request);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public void delete(String id) {
        roleRepository.findById(id).ifPresentOrElse(
                roleRepository::delete,
                () -> {
                    throw new AppException(ErrorCode.ROLE_NOT_FOUND);
                }
        );
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse findByID(String id) {
        return roleRepository.findById(id)
                .map(roleMapper::toRoleResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }
}
