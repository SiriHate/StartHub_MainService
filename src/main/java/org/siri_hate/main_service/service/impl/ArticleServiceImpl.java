package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.siri_hate.main_service.dto.ArticleFullResponseDTO;
import org.siri_hate.main_service.dto.ArticlePageResponseDTO;
import org.siri_hate.main_service.dto.ArticleRequestDTO;
import org.siri_hate.main_service.model.mapper.ArticleMapper;
import org.siri_hate.main_service.model.entity.article.Article;
import org.siri_hate.main_service.repository.ArticleRepository;
import org.siri_hate.main_service.repository.adapters.ArticleSpecification;
import org.siri_hate.main_service.service.ArticleCategoryService;
import org.siri_hate.main_service.service.ArticleService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCategoryService articleCategoryService;
    private final ArticleMapper articleMapper;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            ArticleMapper articleMapper,
            ArticleCategoryService articleCategoryService,
            UserService userService
    )
    {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articleCategoryService = articleCategoryService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public ArticleFullResponseDTO createArticle(String username, ArticleRequestDTO request) {
        Article article = articleMapper.toArticle(request);
        article.setAuthor(userService.findOrCreateUser(username));
        article.setCategory(articleCategoryService.getArticleCategoryEntityById(article.getCategory().getId()));
        articleRepository.save(article);
        return articleMapper.toArticleFullResponseDTO(article);
    }

    @Override
    @Transactional
    public ArticleFullResponseDTO getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return articleMapper.toArticleFullResponseDTO(article);
    }

    @Override
    public ArticlePageResponseDTO getArticles(String category, String query, int page, int size, boolean isModerationPassed) {
        Specification<Article> spec = Specification.allOf(
                ArticleSpecification.titleStartsWith(query),
                ArticleSpecification.hasCategory(category),
                ArticleSpecification.moderationPassed(isModerationPassed)
        );
        Page<Article> articles = articleRepository.findAll(spec, PageRequest.of(page, size));
        if (articles.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return articleMapper.toArticlePageResponseDTO(articles);
    }

    @Override
    public ArticlePageResponseDTO getArticles(String username, String query, int page, int size) {
        Specification<Article> specification = Specification.allOf(
                ArticleSpecification.hasUserUsername(username),
                ArticleSpecification.titleStartsWith(query)
        );
        Page<Article> articles = articleRepository.findAll(specification, PageRequest.of(page, size));
        if (articles.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return articleMapper.toArticlePageResponseDTO(articles);
    }

    @Override
    @Transactional
    public ArticleFullResponseDTO updateArticle(Long id, ArticleRequestDTO request) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        article = articleMapper.articleUpdate(request, article);
        articleRepository.save(article);
        return articleMapper.toArticleFullResponseDTO(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        articleRepository.delete(article);
    }

    @Override
    @Transactional
    public void updateArticleModerationStatus(Long id, Boolean moderationPassed) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        article.setModerationPassed(moderationPassed);
        articleRepository.save(article);
    }
}