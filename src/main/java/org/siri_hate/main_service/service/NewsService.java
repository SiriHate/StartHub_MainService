package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.NewsFullResponseDTO;
import org.siri_hate.main_service.dto.NewsPageResponseDTO;
import org.siri_hate.main_service.dto.NewsRequestDTO;

public interface NewsService {
    NewsFullResponseDTO createNews(String ownerUsername, NewsRequestDTO request);

    NewsFullResponseDTO getNewsById(Long id);

    NewsPageResponseDTO getNews(String category, String query, int page, int size, boolean isModerationPassed);

    NewsFullResponseDTO updateNews(Long id, NewsRequestDTO request);

    void deleteNews(Long id);

    void updateNewsModerationStatus(Long id, Boolean moderationPassed);

    NewsPageResponseDTO getNews(String username, String query, int page, int size);
}