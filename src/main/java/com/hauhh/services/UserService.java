package com.hauhh.services;

import com.hauhh.controllers.request.UserCreationRequest;
import com.hauhh.controllers.request.UserUpdateRequest;
import com.hauhh.controllers.response.PageResponse;
import com.hauhh.controllers.response.UserDetailResponse;
import com.hauhh.controllers.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    UserResponse updateUser(String userID, UserUpdateRequest request);

    void deleteUserByID(String userID);

    UserResponse findUserByID(String userID);

    List<UserResponse> getAllUser();

    UserResponse getUserInfo();

    PageResponse<List<UserDetailResponse>> getUserUsingSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<List<UserDetailResponse>> getUserSortByMultipleColumn(int pageNo, int pageSize, String... sortBy);

    PageResponse<List<UserDetailResponse>> getAllUserWithSortByMultipleColumnAndSearch(int pageNo, int pageSize, String search, String sortBy);
}
