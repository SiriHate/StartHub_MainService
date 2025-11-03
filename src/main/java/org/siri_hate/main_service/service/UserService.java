package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.dto.kafka.UserDeletionMessage;
import org.siri_hate.main_service.model.dto.response.article.ArticleSummaryResponse;
import org.siri_hate.main_service.model.dto.response.news.NewsSummaryResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectSummaryResponse;
import org.siri_hate.main_service.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findOrCreateUser(String username);

    Page<ArticleSummaryResponse> getMyArticles(String username, String query, Pageable pageable);

    Page<NewsSummaryResponse> getMyNews(String username, String query, Pageable pageable);

    Page<ProjectSummaryResponse> getProjectsAsOwner(String username, String query, Pageable pageable);

    Page<ProjectSummaryResponse> getProjectsAsMember(String username, String query, Pageable pageable);

    void deleteUserByUsername(String username);

    void deleteUserByUsername(UserDeletionMessage message);
}
