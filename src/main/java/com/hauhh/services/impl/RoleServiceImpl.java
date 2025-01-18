package com.hauhh.services.impl;

import com.hauhh.controllers.request.RoleCreationRequest;
import com.hauhh.controllers.request.RoleUpdateRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.exceptions.BusinessException;
import com.hauhh.mappers.RoleMapper;
import com.hauhh.models.enums.ErrorConstant;
import com.hauhh.repositories.PermissionRepository;
import com.hauhh.repositories.RoleRepository;
import com.hauhh.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponse createRole(RoleCreationRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(String id, RoleUpdateRequest request) {
        var role = roleRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorConstant.ROLE_NOT_FOUND));

        roleMapper.updateRole(role, request);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public void deleteRole(String id) {
        roleRepository.findById(id).ifPresentOrElse(
                roleRepository::delete,
                () -> {
                    throw new BusinessException(ErrorConstant.ROLE_NOT_FOUND);
                }
        );
    }

    @Override
    public List<RoleResponse> findAllRole() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse findRoleByID(String id) {
        return roleRepository.findById(id)
                .map(roleMapper::toRoleResponse)
                .orElseThrow(() -> new BusinessException(ErrorConstant.ROLE_NOT_FOUND));
    }
}
