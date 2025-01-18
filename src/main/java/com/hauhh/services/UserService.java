package com.hauhh.services;

import com.hauhh.controllers.request.UserCreationRequest;
import com.hauhh.controllers.request.UserUpdateRequest;
import com.hauhh.controllers.response.PageResponse;
import com.hauhh.controllers.response.UserDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDetailResponse createUser(UserCreationRequest request);

    UserDetailResponse updateUser(String id, UserUpdateRequest request);

    void deleteUser(String id);

    UserDetailResponse findUserByID(String id);

    List<UserDetailResponse> findAllUser();

    UserDetailResponse getUser();

    PageResponse<List<UserDetailResponse>> getUserUsingSort(int pageNo, int pageSize, String sortBy);

    PageResponse<List<UserDetailResponse>> getUserSortByMultipleColumn(int pageNo, int pageSize, String... sortBy);

    PageResponse<List<UserDetailResponse>> getUserWithSortByMultipleColumnAndSearch(int pageNo, int pageSize, String search, String sortBy);
}
