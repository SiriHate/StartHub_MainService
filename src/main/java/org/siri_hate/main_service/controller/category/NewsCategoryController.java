package org.siri_hate.main_service.controller.category;

import jakarta.validation.constraints.Positive;
import org.siri_hate.main_service.model.dto.request.category.NewsCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.NewsCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.NewsCategorySummaryResponse;
import org.siri_hate.main_service.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/main_service/news_categories")
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    @Autowired
    public NewsCategoryController(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    @PostMapping
    public ResponseEntity<String> createNewsCategory(@RequestBody NewsCategoryRequest request) {
        newsCategoryService.createNewsCategory(request);
        return new ResponseEntity<>("News category was successfully created!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NewsCategorySummaryResponse>> getAllNewsCategory() {
        List<NewsCategorySummaryResponse> newsCategories = newsCategoryService.getAllNewsCategory();
        return new ResponseEntity<>(newsCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryFullResponse> getNewsCategoryById(
            @Positive @PathVariable Long id) {
        NewsCategoryFullResponse newsCategory = newsCategoryService.getNewsCategoryById(id);
        return new ResponseEntity<>(newsCategory, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateNewsCategory(
            @Positive @PathVariable Long id, @RequestBody NewsCategoryRequest request) {
        newsCategoryService.updateNewsCategory(id, request);
        return new ResponseEntity<>("News category was successfully updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsCategory(@Positive @PathVariable Long id) {
        newsCategoryService.deleteNewsCategory(id);
        return new ResponseEntity<>("News category was successfully deleted!", HttpStatus.NO_CONTENT);
    }
}
