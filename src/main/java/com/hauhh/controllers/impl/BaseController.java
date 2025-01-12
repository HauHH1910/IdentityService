package com.hauhh.controllers.impl;

import com.hauhh.commons.ResponseData;
import com.hauhh.controllers.response.PageResponse;
import com.hauhh.services.BaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<R, C, U> {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    protected final BaseService<R, C, U> service;

    public BaseController(BaseService<R, C, U> service) {
        this.service = service;
    }


    @PostMapping
    public ResponseData<R> create(@RequestBody @Valid C request) {
        LOGGER.info("[CREATE]");
        return ResponseData.<R>builder()
                .code(1000)
                .message("Created")
                .result(service.create(request))
                .build();
    }


    @GetMapping
    public ResponseData<List<R>> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        LOGGER.info("User: {}", authentication.getName());
        LOGGER.info("Role: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<List<R>>builder()
                .message("Get all")
                .result(service.findAll())
                .build();
    }


    @GetMapping("/info")
    public ResponseData<R> getMyInfo() {
        return ResponseData.<R>builder()
                .message("Get info")
                .result(service.get())
                .build();
    }


    @GetMapping("/get/{id}")
    public ResponseData<R> get(@PathVariable String id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        LOGGER.info("Username: {}", authentication.getName());
        LOGGER.info("Role when get User: {}", authentication.getAuthorities().stream().toList());

        return ResponseData.<R>builder()
                .message("Get info")
                .result(service.findByID(id))
                .build();
    }


    @PutMapping("/{id}")
    public ResponseData<R> update(@PathVariable String id, @RequestBody U request) {
        return ResponseData.<R>builder()
                .message("Update user")
                .result(service.update(id, request))
                .build();
    }


    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseData.<Void>builder()
                .message("Deleted")
                .build();
    }


    @GetMapping("/sort")
    public ResponseData<PageResponse<List<R>>> GetAllByUsingSortBy
            (@RequestParam(defaultValue = "0", required = false) int pageNo,
             @Min(10) @RequestParam(defaultValue = "20") int pageSize,
             @RequestParam(required = false) String sortBy) {
        return ResponseData.<PageResponse<List<R>>>builder()
                .message("Get all using sort")
                .result(service.getUsingSort(pageNo, pageSize, sortBy))
                .build();
    }


    @GetMapping("/criteria")
    public ResponseData<PageResponse<List<R>>> GetAllByUsingSortByMultipleColumn
            (@RequestParam(defaultValue = "0", required = false) int pageNo,
             @Min(10) @RequestParam(defaultValue = "20") int pageSize,
             @RequestParam(required = false) String... sortBy) {
        return ResponseData.<PageResponse<List<R>>>builder()
                .message("Get all using criteria")
                .result(service.getSortByMultipleColumn(pageNo, pageSize, sortBy))
                .build();
    }


    @GetMapping("/specification")
    public ResponseData<PageResponse<List<R>>> GetAllByUsingSortByMultipleColumnAndSearch
            (@RequestParam(defaultValue = "0", required = false) int pageNo,
             @RequestParam(defaultValue = "20") int pageSize,
             @RequestParam(required = false) String search,
             @RequestParam(required = false) String sortBy) {
        return ResponseData.<PageResponse<List<R>>>builder()
                .message("Get all using specification")
                .result(service.getWithSortByMultipleColumnAndSearch(pageNo, pageSize, search, sortBy))
                .build();
    }
}
