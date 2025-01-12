package com.hauhh.services.impl;

import com.hauhh.controllers.response.PageResponse;
import com.hauhh.services.BaseService;

import java.util.List;

public abstract class BaseServiceImpl<R, C, U> implements BaseService<R, C, U> {
    @Override
    public R create(C request) {
        return null;
    }

    @Override
    public R update(String id, U request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public R findByID(String id) {
        return null;
    }

    @Override
    public List<R> findAll() {
        return List.of();
    }

    @Override
    public R get() {
        return null;
    }

    @Override
    public PageResponse<List<R>> getUsingSort(int pageNo, int pageSize, String sortBy) {
        return null;
    }

    @Override
    public PageResponse<List<R>> getSortByMultipleColumn(int pageNo, int pageSize, String... sortBy) {
        return null;
    }

    @Override
    public PageResponse<List<R>> getWithSortByMultipleColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        return null;
    }
}
