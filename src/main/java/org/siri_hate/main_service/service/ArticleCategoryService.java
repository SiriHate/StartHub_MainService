package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.ArticleCategoryFullResponseDTO;
import org.siri_hate.main_service.dto.ArticleCategoryRequestDTO;
import org.siri_hate.main_service.dto.ArticleCategorySummaryResponseDTO;
import org.siri_hate.main_service.model.entity.article.ArticleCategory;

import java.util.List;

public interface ArticleCategoryService {
    ArticleCategoryFullResponseDTO createArticleCategory(ArticleCategoryRequestDTO request);

    List<ArticleCategorySummaryResponseDTO> getArticleCategories();

    ArticleCategoryFullResponseDTO getArticleCategory(Long id);

    ArticleCategoryFullResponseDTO updateArticleCategory(Long id, ArticleCategoryRequestDTO request);

    void deleteArticleCategory(Long id);

    ArticleCategory getArticleCategoryEntityById(Long id);
}
