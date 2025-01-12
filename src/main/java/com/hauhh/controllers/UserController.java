package com.hauhh.controllers;

import com.hauhh.controllers.request.UserCreationRequest;
import com.hauhh.controllers.request.UserUpdateRequest;
import com.hauhh.controllers.response.UserDetailResponse;
import com.hauhh.services.impl.BaseServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "User Controller")
@RequestMapping("/api/users")
public class UserController extends BaseController<UserDetailResponse, UserCreationRequest, UserUpdateRequest> {

    public UserController(BaseServiceImpl<UserDetailResponse, UserCreationRequest, UserUpdateRequest> service) {
        super(service);
    }



}
