package com.todo.task;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecefication {

    public static Specification<Task> hasCompanyId(long companyId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("owner").get("company").get("id"), companyId);
    }

    public static Specification<Task> hasUserId(long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("owner").get("id"), userId);
    }

    public static Specification<Task> hasTaskStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
}
