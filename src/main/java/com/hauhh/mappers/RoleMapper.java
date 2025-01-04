package com.hauhh.mappers;

import com.hauhh.controllers.request.RoleRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
