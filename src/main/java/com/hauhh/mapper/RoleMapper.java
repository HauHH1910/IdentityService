package com.hauhh.mapper;

import com.hauhh.dto.request.RoleRequest;
import com.hauhh.dto.response.RoleResponse;
import com.hauhh.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
