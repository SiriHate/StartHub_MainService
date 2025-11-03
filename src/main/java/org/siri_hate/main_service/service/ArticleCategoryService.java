package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.dto.request.category.ArticleCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.ArticleCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.ArticleCategorySummaryResponse;
import org.siri_hate.main_service.model.entity.category.ArticleCategory;

import java.util.List;

public interface ArticleCategoryService {

    void createArticleCategory(ArticleCategoryRequest request);

    List<ArticleCategorySummaryResponse> getAllArticleCategory();

    ArticleCategoryFullResponse getArticleCategoryById(Long id);

    ArticleCategory getArticleCategoryEntityById(Long id);

    void updateArticleCategory(Long id, ArticleCategoryRequest request);

    void deleteArticleCategory(Long id);
}
