package com.hauhh.services.impl;

import com.hauhh.controllers.request.UserCreationRequest;
import com.hauhh.controllers.request.UserUpdateRequest;
import com.hauhh.controllers.response.PageResponse;
import com.hauhh.controllers.response.UserDetailResponse;
import com.hauhh.models.User;
import com.hauhh.models.enums.ErrorConstant;
import com.hauhh.exceptions.BusinessException;
import com.hauhh.mappers.UserMapper;
import com.hauhh.repositories.RoleRepository;
import com.hauhh.repositories.SearchRepository;
import com.hauhh.repositories.UserRepository;
import com.hauhh.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final SearchRepository searchRepository;

    private static final String REGEX_SORT_BY = "(\\w+?)(:)(.*)";

    @Override
    public UserDetailResponse createUser(UserCreationRequest request) {
        log.info("Create User");

        if (userRepository.existsByUsername(request.getUsername()))
            throw new BusinessException(ErrorConstant.USER_EXIST);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserDetailResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorConstant.USER_NOT_EXIST));

        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new BusinessException(ErrorConstant.USER_NOT_EXIST);
                        });
    }

    @Override
    public UserDetailResponse findUserByID(String id) {
        log.info("In method findUserByID");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorConstant.USER_NOT_EXIST));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserDetailResponse> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserDetailResponse getUser() {
        String name = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByUsername(name)
                .orElseThrow(() -> new BusinessException(ErrorConstant.USER_NOT_EXIST));

        return userMapper
                .toUserResponse(user);
    }

    @Override
    public PageResponse<List<UserDetailResponse>> getUserUsingSort(int pageNo, int pageSize, String sortBy) {
        int defaultValuePage = 0;
        if (pageNo > 0) {
            defaultValuePage = pageNo - 1;
        }

        List<Sort.Order> sortOrders = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(REGEX_SORT_BY);

            Matcher matcher = pattern.matcher(sortBy);

            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("ASC")) {
                    sortOrders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else if (matcher.group(3).equalsIgnoreCase("DESC")) {
                    sortOrders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(defaultValuePage, pageSize, Sort.by(sortOrders));

        Page<User> userPage = userRepository.findAll(pageable);

        return PageResponse.<List<UserDetailResponse>>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .items(userPage.stream().map(u -> UserDetailResponse.builder()
                                .id(u.getId())
                                .username(u.getUsername())
                                .dob(u.getDob())
                                .firstName(u.getFirstName())
                                .lastName(u.getLastName())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public PageResponse<List<UserDetailResponse>> getUserSortByMultipleColumn(int pageNo, int pageSize, String... sortBy) {
        if (pageNo > 0) {
            pageNo = pageNo - 1;
        }

        List<Sort.Order> sortOrders = new ArrayList<>();

        for (String sorts : sortBy) {
            Pattern pattern = Pattern.compile(REGEX_SORT_BY);

            Matcher matcher = pattern.matcher(sorts);

            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("ASC")) {
                    sortOrders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else if (matcher.group(3).equalsIgnoreCase("DESC")) {
                    sortOrders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrders));

        Page<User> userPage = userRepository.findAll(pageable);

        return PageResponse.<List<UserDetailResponse>>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .items(userPage.stream().map(u -> UserDetailResponse.builder()
                                .id(u.getId())
                                .username(u.getUsername())
                                .dob(u.getDob())
                                .firstName(u.getFirstName())
                                .lastName(u.getLastName())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public PageResponse<List<UserDetailResponse>> getUserWithSortByMultipleColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        return searchRepository.getAllUserWithSortByMultipleColumnAndSearch(pageNo, pageSize, search, sortBy);
    }
}

