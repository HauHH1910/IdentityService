package com.hauhh.services;

import com.hauhh.controllers.response.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<R, C, U> {

    R create(C request);

    R update(String id, U request);

    void delete(String id);

    R findByID(String id);

    List<R> findAll();

    R get();

    PageResponse<List<R>> getUsingSort(int pageNo, int pageSize, String sortBy);

    PageResponse<List<R>> getSortByMultipleColumn(int pageNo, int pageSize, String... sortBy);

    PageResponse<List<R>> getWithSortByMultipleColumnAndSearch(int pageNo, int pageSize, String search, String sortBy);
}
