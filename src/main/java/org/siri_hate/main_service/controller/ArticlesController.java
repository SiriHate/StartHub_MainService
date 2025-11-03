package org.siri_hate.main_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.siri_hate.main_service.model.dto.request.article.ArticleFullRequest;
import org.siri_hate.main_service.model.dto.response.article.ArticleFullResponse;
import org.siri_hate.main_service.model.dto.response.article.ArticleSummaryResponse;
import org.siri_hate.main_service.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/main_service/articles")
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@Valid @RequestBody ArticleFullRequest article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        articleService.createArticle(username, article);
        return new ResponseEntity<>("Article was successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleFullResponse> getArticleById(@PathVariable Long id) {
        ArticleFullResponse article = articleService.getArticleById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleSummaryResponse>> getAllArticles(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean moderationPassed,
            @PageableDefault() Pageable pageable) {
        Page<ArticleSummaryResponse> articles;
        if (moderationPassed != null) {
            articles = moderationPassed ?
                    articleService.getModeratedArticles(category, query, pageable) :
                    articleService.getUnmoderatedArticles(category, query, pageable);
        } else {
            articles = articleService.getArticlesByCategoryAndSearchQuery(category, query, pageable);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateArticle(
            @PathVariable Long id, @Valid @RequestBody ArticleFullRequest article) {
        articleService.updateArticle(id, article);
        return new ResponseEntity<>("Article has been successfully modified", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>("Article has been successfully deleted", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/moderationPassed")
    public ResponseEntity<String> updateArticleModerationStatus(
            @PathVariable @Positive Long id,
            @RequestBody Boolean moderationPassed) {
        articleService.updateArticleModerationStatus(id, moderationPassed);
        return new ResponseEntity<>("Article moderation status has been successfully updated", HttpStatus.OK);
    }
}
