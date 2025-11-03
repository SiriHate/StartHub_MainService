package org.siri_hate.main_service.controller.category;

import jakarta.validation.constraints.Positive;
import org.siri_hate.main_service.model.dto.request.category.ArticleCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.ArticleCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.ArticleCategorySummaryResponse;
import org.siri_hate.main_service.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/main_service/article_categories")
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    @Autowired
    public ArticleCategoryController(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @PostMapping
    public ResponseEntity<String> createArticleCategory(@RequestBody ArticleCategoryRequest request) {
        articleCategoryService.createArticleCategory(request);
        return new ResponseEntity<>("Article category was successfully created!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArticleCategorySummaryResponse>> getAllArticleCategory() {
        List<ArticleCategorySummaryResponse> articleCategories = articleCategoryService.getAllArticleCategory();
        return new ResponseEntity<>(articleCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleCategoryFullResponse> getArticleCategoryById(@Positive @PathVariable Long id) {
        ArticleCategoryFullResponse articleCategory = articleCategoryService.getArticleCategoryById(id);
        return new ResponseEntity<>(articleCategory, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateArticleCategory(@Positive @PathVariable Long id, @RequestBody ArticleCategoryRequest request) {
        articleCategoryService.updateArticleCategory(id, request);
        return new ResponseEntity<>("Article category was successfully updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticleCategory(@Positive @PathVariable Long id) {
        articleCategoryService.deleteArticleCategory(id);
        return new ResponseEntity<>("Article category was successfully deleted!", HttpStatus.NO_CONTENT);
    }
}
