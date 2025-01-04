package com.hauhh.mappers;

import com.hauhh.controllers.request.PermissionRequest;
import com.hauhh.controllers.response.PermissionResponse;
import com.hauhh.models.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionRequest permissionRequest) {
        if ( permissionRequest == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( permissionRequest.getName() );
        permission.description( permissionRequest.getDescription() );

        return permission.build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.name( permission.getName() );
        permissionResponse.description( permission.getDescription() );

        return permissionResponse.build();
    }
}
