package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.dto.request.news.NewsFullRequest;
import org.siri_hate.main_service.model.dto.response.news.NewsFullResponse;
import org.siri_hate.main_service.model.dto.response.news.NewsSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    void createNews(String username, NewsFullRequest news);

    NewsFullResponse getNewsById(Long id);

    Page<NewsSummaryResponse> getNewsByCategoryAndSearchQuery(
            String category, String query, Pageable pageable);

    Page<NewsSummaryResponse> getModeratedNews(String category, String query, Pageable pageable);

    Page<NewsSummaryResponse> getUnmoderatedNews(String category, String query, Pageable pageable);

    void updateNews(Long id, NewsFullRequest news);

    void deleteNews(Long id);

    void updateNewsModerationStatus(Long id, Boolean moderationPassed);

    Page<NewsSummaryResponse> getNewsByUser(String username, String query, Pageable pageable);
}
