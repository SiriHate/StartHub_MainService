package org.siri_hate.main_service.repository.specification;

import jakarta.persistence.criteria.Join;
import org.siri_hate.main_service.model.entity.news.News;
import org.siri_hate.main_service.model.entity.project.Project;
import org.siri_hate.main_service.model.entity.project.ProjectCategory;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {

    public static Specification<Project> projectNameStartsWith(String projectName) {
        return (root, query, criteriaBuilder) -> {
            if (projectName == null || projectName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("projectName"), projectName + "%");
        };
    }

    public static Specification<Project> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Project, ProjectCategory> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("name"), category);
        };
    }

    public static Specification<Project> moderationPassed(Boolean moderationPassed) {
        if (moderationPassed == null) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(root.get("moderationPassed"), moderationPassed);
    }

    public static Specification<Project> hasUsername(String username) {
        return (root, query, cb) -> {
            if (username == null || username.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("owner").get("username"), username);
        };
    }
}