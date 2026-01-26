package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.ArticleFullResponseDTO;
import org.siri_hate.main_service.dto.ArticlePageResponseDTO;
import org.siri_hate.main_service.dto.ArticleRequestDTO;

public interface ArticleService {
    ArticleFullResponseDTO createArticle(String ownerUsername, ArticleRequestDTO request);

    ArticleFullResponseDTO getArticle(Long id);

    ArticlePageResponseDTO getArticles(String category, String query, int page, int size, boolean isModerationPassed);

    ArticleFullResponseDTO updateArticle(Long id, ArticleRequestDTO article);

    void deleteArticle(Long id);

    void updateArticleModerationStatus(Long id, Boolean moderationPassed);

    ArticlePageResponseDTO getArticles(String username, String query, int page, int size);
}