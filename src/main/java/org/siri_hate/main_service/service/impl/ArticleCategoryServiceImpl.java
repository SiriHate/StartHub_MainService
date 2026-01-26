package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.siri_hate.main_service.dto.ArticleCategoryFullResponseDTO;
import org.siri_hate.main_service.dto.ArticleCategorySummaryResponseDTO;
import org.siri_hate.main_service.model.mapper.ArticleCategoryMapper;
import org.siri_hate.main_service.model.entity.article.ArticleCategory;
import org.siri_hate.main_service.repository.ArticleCategoryRepository;
import org.siri_hate.main_service.service.ArticleCategoryService;
import org.siri_hate.main_service.dto.ArticleCategoryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    private final ArticleCategoryRepository articleCategoryRepository;
    private final ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    public ArticleCategoryServiceImpl(ArticleCategoryRepository articleCategoryRepository, ArticleCategoryMapper articleCategoryMapper) {
        this.articleCategoryRepository = articleCategoryRepository;
        this.articleCategoryMapper = articleCategoryMapper;
    }

    @Override
    @Transactional
    public ArticleCategoryFullResponseDTO createArticleCategory(ArticleCategoryRequestDTO request) {
        ArticleCategory articleCategory = articleCategoryMapper.toArticleCategory(request);
        articleCategoryRepository.save(articleCategory);
        return articleCategoryMapper.toArticleCategoryFullResponse(articleCategory);
    }

    @Override
    public List<ArticleCategorySummaryResponseDTO> getArticleCategories() {
        List<ArticleCategory> articleCategories = articleCategoryRepository.findAll();
        if (articleCategories.isEmpty()) {
            throw new EntityNotFoundException("No article categories found!");
        }
        return articleCategoryMapper.toArticleCategorySummaryResponseList(articleCategories);
    }

    @Override
    public ArticleCategoryFullResponseDTO getArticleCategory(Long id) {
        ArticleCategory articleCategory = articleCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return articleCategoryMapper.toArticleCategoryFullResponse(articleCategory);
    }

    @Override
    public ArticleCategory getArticleCategoryEntityById(Long id) {
        return articleCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public ArticleCategoryFullResponseDTO updateArticleCategory(Long id, ArticleCategoryRequestDTO request) {
        ArticleCategory articleCategory = articleCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        articleCategory = articleCategoryMapper.updateArticleCategory(request, articleCategory);
        articleCategoryRepository.save(articleCategory);
        return articleCategoryMapper.toArticleCategoryFullResponse(articleCategory);
    }

    @Override
    @Transactional
    public void deleteArticleCategory(Long id) {
        ArticleCategory articleCategory = articleCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        articleCategoryRepository.delete(articleCategory);
    }
}