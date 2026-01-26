package org.siri_hate.main_service.repository.adapters;

import jakarta.persistence.criteria.Join;
import org.siri_hate.main_service.model.entity.article.Article;
import org.siri_hate.main_service.model.entity.article.ArticleCategory;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

    public static Specification<Article> titleStartsWith(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(root.get("title"), title + "%");
    }

    public static Specification<Article> hasCategory(String category) {
        if (category == null || category.isBlank()) {
            return null;
        }
        return (root, query, cb) -> {
            Join<Article, ArticleCategory> categoryJoin = root.join("category");
            return cb.equal(categoryJoin.get("name"), category);
        };
    }

    public static Specification<Article> moderationPassed(Boolean moderationPassed) {
        if (moderationPassed == null) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(root.get("moderationPassed"), moderationPassed);
    }

    public static Specification<Article> hasUserUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(root.get("user").get("username"), username);
    }
}
