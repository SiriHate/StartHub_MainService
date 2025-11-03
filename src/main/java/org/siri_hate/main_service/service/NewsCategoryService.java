package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.dto.request.category.NewsCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.NewsCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.NewsCategorySummaryResponse;
import org.siri_hate.main_service.model.entity.category.NewsCategory;

import java.util.List;

public interface NewsCategoryService {

    void createNewsCategory(NewsCategoryRequest request);

    NewsCategory getNewsCategoryEntityById(Long id);

    List<NewsCategorySummaryResponse> getAllNewsCategory();

    NewsCategoryFullResponse getNewsCategoryById(Long id);

    void updateNewsCategory(Long id, NewsCategoryRequest request);

    void deleteNewsCategory(Long id);
}
