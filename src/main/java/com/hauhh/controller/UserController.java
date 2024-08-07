package com.hauhh.controller;

import com.hauhh.common.ResponseData;
import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.request.UserUpdateRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.entity.User;
import com.hauhh.enums.ResponseCode;
import com.hauhh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseData<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        return ResponseData.<UserResponse>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ResponseData<List<UserResponse>> getAllUser() {
        return ResponseData.<List<UserResponse>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .result(userService.getAllUser())
                .build();
    }

    @GetMapping("/myInfo")
    public ResponseData<UserResponse> getMyInfo() {
        return ResponseData.<UserResponse>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .result(userService.getUserInfo())
                .build();
    }

    @GetMapping("/getUser/{userID}")
    public ResponseData<UserResponse> getUser(@PathVariable String userID) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        log.info("Role: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<UserResponse>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .result(userService.findUserByID(userID))
                .build();
    }

    @PutMapping("/{userID}")
    public ResponseData<UserResponse> updateUser(@PathVariable String userID, @RequestBody UserUpdateRequest request) {
        return ResponseData.<UserResponse>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .result(userService.updateUser(userID, request))
                .build();
    }

    @DeleteMapping("/{userID}")
    public ResponseData<Void> deleteUser(@PathVariable String userID) {
        userService.deleteUserByID(userID);
        return ResponseData.<Void>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .build();
    }


}
