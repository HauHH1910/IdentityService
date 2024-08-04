package com.hauhh.service;

import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.request.UserUpdateRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    UserResponse updateUser(String userID, UserUpdateRequest request);

    void deleteUserByID(String userID);

    UserResponse findUserByID(String userID);

    List<UserResponse> getAllUser();
}
