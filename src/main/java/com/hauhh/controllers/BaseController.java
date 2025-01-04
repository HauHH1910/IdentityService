package com.hauhh.controllers;

import com.hauhh.commons.ResponseData;
import com.hauhh.controllers.response.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<
        R,
        C,
        U
        > {

    //!NOTE: R - Response | C - Creation | U - Updated

    @PostMapping
    ResponseData<R> create(@RequestBody @Valid C request);

    @GetMapping
    ResponseData<List<R>> getAll();

    @GetMapping("/info")
    ResponseData<R> getMyInfo();

    @GetMapping("/get/{id}")
    ResponseData<R> get(@PathVariable String id);

    @PutMapping("/{id}")
    ResponseData<R> update(@PathVariable String id, @RequestBody U request);

    @DeleteMapping("/{id}")
    ResponseData<Void> delete(@PathVariable String id);

    @GetMapping("/sort")
    ResponseData<PageResponse<List<R>>> GetAllByUsingSortBy
            (@RequestParam(defaultValue = "0", required = false) int pageNo,
                @Min(10) @RequestParam(defaultValue = "20") int pageSize,
                @RequestParam(required = false) String sortBy);

    @GetMapping("/criteria")
    ResponseData<PageResponse<List<R>>> GetAllByUsingSortByMultipleColumn
            (@RequestParam(defaultValue = "0", required = false) int pageNo,
                @Min(10) @RequestParam(defaultValue = "20") int pageSize,
                @RequestParam(required = false) String... sortBy);

    @GetMapping("/specification")
    ResponseData<PageResponse<List<R>>> GetAllByUsingSortByMultipleColumnAndSearch
            (@RequestParam(defaultValue = "0", required = false) int pageNo,
                @RequestParam(defaultValue = "20") int pageSize,
                @RequestParam(required = false) String search,
                @RequestParam(required = false) String sortBy);

}
