package org.siri_hate.main_service.service.impl;

import jakarta.transaction.Transactional;
import org.siri_hate.main_service.model.dto.mapper.ArticleCategoryMapper;
import org.siri_hate.main_service.model.dto.request.category.ArticleCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.ArticleCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.ArticleCategorySummaryResponse;
import org.siri_hate.main_service.model.entity.category.ArticleCategory;
import org.siri_hate.main_service.repository.ArticleCategoryRepository;
import org.siri_hate.main_service.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public void createArticleCategory(@RequestBody ArticleCategoryRequest request) {
        ArticleCategory articleCategoryEntity = articleCategoryMapper.toArticleCategory(request);
        articleCategoryRepository.save(articleCategoryEntity);
    }

    @Override
    public List<ArticleCategorySummaryResponse> getAllArticleCategory() {
        List<ArticleCategory> articleCategories = articleCategoryRepository.findAll();
        if (articleCategories.isEmpty()) {
            throw new RuntimeException("No article categories found!");
        }
        return articleCategoryMapper.toArticleCategorySummaryResponseList(articleCategories);
    }

    @Override
    public ArticleCategoryFullResponse getArticleCategoryById(Long id) {
        ArticleCategory articleCategory = articleCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No article category with id: " + id));
        return articleCategoryMapper.toArticleCategoryFullResponse(articleCategory);
    }

    @Override
    public ArticleCategory getArticleCategoryEntityById(Long id) {
        return articleCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No article category with id: " + id));
    }

    @Override
    @Transactional
    public void updateArticleCategory(Long id, ArticleCategoryRequest request) {
        ArticleCategory articleCategory = articleCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No article category with id: " + id));
        articleCategoryMapper.updateArticleCategoryFromRequest(request, articleCategory);
        articleCategoryRepository.save(articleCategory);
    }

    @Override
    @Transactional
    public void deleteArticleCategory(Long id) {
        ArticleCategory articleCategory = articleCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No article category with id: " + id));
        articleCategoryRepository.delete(articleCategory);
    }
}
