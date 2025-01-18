package com.hauhh.repositories;

import com.hauhh.controllers.response.PageResponse;
import com.hauhh.controllers.response.UserDetailResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SearchRepository {

    private final static String REGEX_SORT_BY = "(\\w+?)(:)(.*)";
    private final static String LIKE_FORMAT = "%%%s%%";

    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<List<UserDetailResponse>> getAllUserWithSortByMultipleColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        StringBuilder sqlQuery = new StringBuilder("SELECT new com.hauhh.dto.response.UserDetailResponse(u.id, u.username, u.firstName, u.lastName, u.dob) FROM User u WHERE 1=1");

        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" AND LOWER(u.firstName) LIKE LOWER(:firstName)");
            sqlQuery.append(" OR LOWER(u.lastName) LIKE LOWER(:lastName)");
            sqlQuery.append(" OR LOWER(u.username) LIKE LOWER(:username)");
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(REGEX_SORT_BY);

            Matcher matcher = pattern.matcher(sortBy);

            if (matcher.find()) {
                sqlQuery.append(String.format(" ORDER BY u.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());

        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("firstName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("lastName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("username", String.format(LIKE_FORMAT, search));
        }

        var resultList = selectQuery.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u WHERE 1=1");

        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" AND LOWER(u.firstName) LIKE LOWER(?1)");
            sqlCountQuery.append(" AND LOWER(u.lastName) LIKE LOWER(?2)");
            sqlCountQuery.append(" AND LOWER(u.username) LIKE LOWER(?3)");
        }

        Query selectCountQuery = entityManager.createQuery(sqlCountQuery.toString());

        if (StringUtils.hasLength(search)) {
            selectCountQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            selectCountQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            selectCountQuery.setParameter(3, String.format(LIKE_FORMAT, search));
        }

        Long totalElements = (Long) selectCountQuery.getSingleResult();

        Page<UserDetailResponse> page = new PageImpl<UserDetailResponse>(resultList, PageRequest.of(pageNo, pageSize), totalElements);

        return PageResponse.<List<UserDetailResponse>>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .items(page.stream().toList())
                .build();
    }
}
