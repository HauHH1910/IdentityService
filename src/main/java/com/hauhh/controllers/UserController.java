package com.hauhh.controllers;

import com.hauhh.commons.ResponseData;
import com.hauhh.controllers.request.UserCreationRequest;
import com.hauhh.controllers.request.UserUpdateRequest;
import com.hauhh.controllers.response.PageResponse;
import com.hauhh.controllers.response.UserDetailResponse;
import com.hauhh.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller")
@Slf4j(topic = "User Controller")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create new user", description = "This API used to create new user")
    public ResponseData<UserDetailResponse> create(@RequestBody @Valid UserCreationRequest request) {
        log.info("Create user");
        return ResponseData.<UserDetailResponse>builder()
                .code(1000)
                .message("Created")
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ResponseData<List<UserDetailResponse>> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User: {}", authentication.getName());
        log.info("Role: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<List<UserDetailResponse>>builder()
                .message("Get all users")
                .result(userService.findAllUser())
                .build();
    }

    @GetMapping("/info")
    public ResponseData<UserDetailResponse> getMyInfo() {
        return ResponseData.<UserDetailResponse>builder()
                .message("Get info")
                .result(userService.getUser())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<UserDetailResponse> get(@PathVariable String id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        log.info("Role when get User: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<UserDetailResponse>builder()
                .message("Get info")
                .result(userService.findUserByID(id))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<UserDetailResponse> update(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        return ResponseData.<UserDetailResponse>builder()
                .message("Update user")
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseData.<Void>builder()
                .message("Deleted")
                .build();
    }

    @GetMapping("/sort")
    public ResponseData<PageResponse<List<UserDetailResponse>>> GetAllByUsingSortBy
            (
                    @RequestParam(defaultValue = "0", required = false) int pageNo,
                    @Min(10) @RequestParam(defaultValue = "20") int pageSize,
                    @RequestParam(required = false) String sortBy
            ) {
        return ResponseData.<PageResponse<List<UserDetailResponse>>>builder()
                .message("Get all using sort")
                .result(userService.getUserUsingSort(pageNo, pageSize, sortBy))
                .build();
    }


    @GetMapping("/criteria")
    public ResponseData<PageResponse<List<UserDetailResponse>>> GetAllByUsingSortByMultipleColumn
            (
                    @RequestParam(defaultValue = "0", required = false) int pageNo,
                    @Min(10) @RequestParam(defaultValue = "20") int pageSize,
                    @RequestParam(required = false) String... sortBy
            ) {
        return ResponseData.<PageResponse<List<UserDetailResponse>>>builder()
                .message("Get all using criteria")
                .result(userService.getUserSortByMultipleColumn(pageNo, pageSize, sortBy))
                .build();
    }


    @GetMapping("/specification")
    public ResponseData<PageResponse<List<UserDetailResponse>>> GetAllByUsingSortByMultipleColumnAndSearch
            (
                    @RequestParam(defaultValue = "0", required = false) int pageNo,
                    @RequestParam(defaultValue = "20") int pageSize,
                    @RequestParam(required = false) String search,
                    @RequestParam(required = false) String sortBy
            ) {
        return ResponseData.<PageResponse<List<UserDetailResponse>>>builder()
                .message("Get all using specification")
                .result(userService.getUserWithSortByMultipleColumnAndSearch(pageNo, pageSize, search, sortBy))
                .build();
    }

}
