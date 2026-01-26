package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.NewsCategoryFullResponseDTO;
import org.siri_hate.main_service.dto.NewsCategoryRequestDTO;
import org.siri_hate.main_service.dto.NewsCategorySummaryResponseDTO;
import org.siri_hate.main_service.model.entity.news.NewsCategory;

import java.util.List;

public interface NewsCategoryService {

    NewsCategoryFullResponseDTO createNewsCategory(NewsCategoryRequestDTO request);

    List<NewsCategorySummaryResponseDTO> getNewsCategories();

    NewsCategoryFullResponseDTO getNewsCategory(Long id);

    NewsCategoryFullResponseDTO updateNewsCategory(Long id, NewsCategoryRequestDTO request);

    void deleteNewsCategory(Long id);

    NewsCategory getNewsCategoryEntityById(Long id);
}
