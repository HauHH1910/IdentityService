package com.hauhh.mappers;

import com.hauhh.controllers.request.UserCreationRequest;
import com.hauhh.controllers.request.UserUpdateRequest;
import com.hauhh.controllers.response.UserResponse;
import com.hauhh.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
