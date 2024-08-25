package com.hauhh.service.impl;

import com.hauhh.dto.request.UserCreationRequest;
import com.hauhh.dto.request.UserUpdateRequest;
import com.hauhh.dto.response.UserResponse;
import com.hauhh.entities.User;
import com.hauhh.enums.ErrorCode;
import com.hauhh.exception.AppException;
import com.hauhh.mapper.UserMapper;
import com.hauhh.repository.RoleRepository;
import com.hauhh.repository.UserRepository;
import com.hauhh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(UserCreationRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new AppException(ErrorCode.USER_EXIST);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(String userID, UserUpdateRequest request) {
        User user = userRepository.findById(userID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse findUserByID(String userID) {
        log.info("In method findUserByID");
        User user = userRepository.findById(userID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUserByID(String userID) {
        userRepository.deleteById(userID);
    }


    @Override
    public UserResponse getUserInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('READ_DATA')")
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
}

