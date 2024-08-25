package com.hauhh.service.impl;

import com.hauhh.dto.request.PermissionRequest;
import com.hauhh.dto.response.PermissionResponse;
import com.hauhh.entities.Permission;
import com.hauhh.mapper.PermissionMapper;
import com.hauhh.repository.PermissionRepository;
import com.hauhh.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {

        Permission permission = permissionMapper.toPermission(request);

        permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }
}
