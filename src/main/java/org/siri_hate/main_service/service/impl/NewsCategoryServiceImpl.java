package org.siri_hate.main_service.service.impl;

import jakarta.transaction.Transactional;
import org.siri_hate.main_service.model.dto.mapper.NewsCategoryMapper;
import org.siri_hate.main_service.model.dto.request.category.NewsCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.NewsCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.NewsCategorySummaryResponse;
import org.siri_hate.main_service.model.entity.category.NewsCategory;
import org.siri_hate.main_service.repository.NewsCategoryRepository;
import org.siri_hate.main_service.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;

    @Autowired
    public NewsCategoryServiceImpl(
            NewsCategoryRepository newsCategoryRepository, NewsCategoryMapper newsCategoryMapper) {
        this.newsCategoryRepository = newsCategoryRepository;
        this.newsCategoryMapper = newsCategoryMapper;
    }

    @Override
    @Transactional
    public void createNewsCategory(NewsCategoryRequest request) {
        NewsCategory newsCategoryEntity = newsCategoryMapper.toNewsCategory(request);
        newsCategoryRepository.save(newsCategoryEntity);
    }

    @Override
    public NewsCategory getNewsCategoryEntityById(Long id) {
        return newsCategoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No news category with id: " + id));
    }

    @Override
    public List<NewsCategorySummaryResponse> getAllNewsCategory() {
        List<NewsCategory> newsCategories = newsCategoryRepository.findAll();
        if (newsCategories.isEmpty()) {
            throw new RuntimeException("No news categories found!");
        }
        return newsCategoryMapper.toNewsCategorySummaryResponseList(newsCategories);
    }

    @Override
    public NewsCategoryFullResponse getNewsCategoryById(Long id) {
        NewsCategory newsCategory =
                newsCategoryRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("No news category with id: " + id));
        return newsCategoryMapper.toNewsCategoryFullResponse(newsCategory);
    }

    @Override
    @Transactional
    public void updateNewsCategory(Long id, NewsCategoryRequest request) {
        NewsCategory newsCategory =
                newsCategoryRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("No news category with id: " + id));
        newsCategoryMapper.updateNewsCategoryFromRequest(request, newsCategory);
        newsCategoryRepository.save(newsCategory);
    }

    @Override
    @Transactional
    public void deleteNewsCategory(Long id) {
        NewsCategory newsCategory =
                newsCategoryRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("No news category with id: " + id));
        newsCategoryRepository.delete(newsCategory);
    }
}
