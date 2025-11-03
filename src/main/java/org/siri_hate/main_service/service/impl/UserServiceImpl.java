package org.siri_hate.main_service.service.impl;

import jakarta.transaction.Transactional;
import org.siri_hate.main_service.model.dto.kafka.UserDeletionMessage;
import org.siri_hate.main_service.model.dto.response.article.ArticleSummaryResponse;
import org.siri_hate.main_service.model.dto.response.news.NewsSummaryResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectSummaryResponse;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.repository.UserRepository;
import org.siri_hate.main_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ArticleService articleService;
    private final NewsService newsService;
    private final ProjectService projectService;
    private final KafkaService kafkaService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy ArticleService articleService, NewsService newsService, ProjectService projectService, KafkaService kafkaService) {
        this.userRepository = userRepository;
        this.articleService = articleService;
        this.newsService = newsService;
        this.projectService = projectService;
        this.kafkaService = kafkaService;
    }

    @Override
    public User findOrCreateUser(String username) {
        return userRepository.findUserByUsername(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(username);
            return userRepository.save(newUser);
        });
    }

    @Override
    @Transactional
    public Page<ArticleSummaryResponse> getMyArticles(String username, String query, Pageable pageable) {
        Page<ArticleSummaryResponse> articles = articleService.getArticlesByUser(username, query, pageable);
        if (articles.isEmpty()) {
            throw new RuntimeException("No articles found for user: " + username);
        }
        return articles;
    }

    @Override
    @Transactional
    public Page<NewsSummaryResponse> getMyNews(String username, String query, Pageable pageable) {
        Page<NewsSummaryResponse> news = newsService.getNewsByUser(username, query, pageable);
        if (news.isEmpty()) {
            throw new RuntimeException("No news found for user: " + username);
        }
        return news;
    }

    @Override
    @Transactional
    public Page<ProjectSummaryResponse> getProjectsAsOwner(String username, String query, Pageable pageable) {
        Page<ProjectSummaryResponse> projects = projectService.getProjectsByOwner(username, query, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found for user: " + username);
        }
        return projects;
    }

    @Override
    @Transactional
    public Page<ProjectSummaryResponse> getProjectsAsMember(String username, String query, Pageable pageable) {
        Page<ProjectSummaryResponse> projects = projectService.getProjectsByMember(username, query, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found for user: " + username);
        }
        return projects;
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        userRepository.delete(user);
        kafkaService.sendUserDeletionMessage(username);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(UserDeletionMessage message) {
        String username = message.username();
        User user = userRepository.findUserByUsername(message.username()).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        userRepository.delete(user);
        kafkaService.sendUserDeletionMessage(username);
    }
}
