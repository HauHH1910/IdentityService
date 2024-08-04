package com.hauhh.controller;

import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.request.UserUpdateRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.entity.User;
import com.hauhh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseData<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully created user")
                .data(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ResponseData<List<UserResponse>> getAllUser() {
        return new ResponseData<>(HttpStatus.OK.value(), "Get All Users", userService.getAllUser());
    }

    @GetMapping("/{userID}")
    public ResponseData<UserResponse> getUser(@PathVariable String userID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get User by ID: " + userID, userService.findUserByID(userID));
    }

    @PutMapping("/{userID}")
    public ResponseData<UserResponse> updateUser(@PathVariable String userID, @RequestBody UserUpdateRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Update User by ID: " + userID, userService.updateUser(userID, request));
    }

    @DeleteMapping("/{userID}")
    public ResponseData<User> deleteUser(@PathVariable String userID) {
        userService.deleteUserByID(userID);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete User by ID: " + userID);
    }

}
