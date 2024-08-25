package com.hauhh.mapper;

import com.hauhh.dto.request.PermissionRequest;
import com.hauhh.dto.response.PermissionResponse;
import com.hauhh.entities.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);

}
