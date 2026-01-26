package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.siri_hate.main_service.dto.NewsFullResponseDTO;
import org.siri_hate.main_service.dto.NewsPageResponseDTO;
import org.siri_hate.main_service.dto.NewsRequestDTO;
import org.siri_hate.main_service.model.mapper.NewsMapper;
import org.siri_hate.main_service.model.entity.news.News;
import org.siri_hate.main_service.repository.NewsRepository;
import org.siri_hate.main_service.repository.adapters.NewsSpecification;
import org.siri_hate.main_service.service.NewsCategoryService;
import org.siri_hate.main_service.service.NewsService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final NewsCategoryService newsCategoryService;
    private final UserService userService;

    @Autowired
    public NewsServiceImpl(
            NewsRepository newsRepository,
            NewsMapper newsMapper,
            NewsCategoryService newsCategoryService,
            UserService userService
    )
    {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.newsCategoryService = newsCategoryService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public NewsFullResponseDTO createNews(String username, NewsRequestDTO request) {
        News news = newsMapper.toNews(request);
        news.setAuthor(userService.findOrCreateUser(username));
        news.setCategory(newsCategoryService.getNewsCategoryEntityById(news.getCategory().getId()));
        news.setModerationPassed(false);
        newsRepository.save(news);
        return newsMapper.toNewsFullResponse(news);
    }

    @Override
    public NewsFullResponseDTO getNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return newsMapper.toNewsFullResponse(news);
    }

    @Override
    public NewsPageResponseDTO getNews(String category, String query, int page, int size, boolean isModerationPassed) {
        Specification<News> spec = Specification.allOf(
                NewsSpecification.titleStartsWith(query),
                NewsSpecification.hasCategory(category),
                NewsSpecification.moderationPassed(isModerationPassed)
        );
        Page<News> news = newsRepository.findAll(spec, PageRequest.of(page, size));
        if (news.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return newsMapper.toNewsPageResponse(news);
    }

    @Override
    public NewsPageResponseDTO getNews(String username, String query, int page, int size) {
        Specification<News> spec = Specification.allOf(
                NewsSpecification.hasUserUsername(username),
                NewsSpecification.titleStartsWith(query)
        );
        Page<News> news = newsRepository.findAll(spec, PageRequest.of(page, size));
        if (news.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return newsMapper.toNewsPageResponse(news);
    }

    @Override
    @Transactional
    public NewsFullResponseDTO updateNews(Long id, NewsRequestDTO request) {
        News news = newsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        news = newsMapper.newsUpdate(request, news);
        newsRepository.save(news);
        return newsMapper.toNewsFullResponse(news);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        newsRepository.delete(news);
    }

    @Override
    @Transactional
    public void updateNewsModerationStatus(Long id, Boolean moderationPassed) {
        News news = newsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        news.setModerationPassed(moderationPassed);
        newsRepository.save(news);
    }
}