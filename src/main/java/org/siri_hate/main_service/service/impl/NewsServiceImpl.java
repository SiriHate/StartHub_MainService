package org.siri_hate.main_service.service.impl;

import jakarta.transaction.Transactional;
import org.siri_hate.main_service.model.dto.mapper.NewsMapper;
import org.siri_hate.main_service.model.dto.request.news.NewsFullRequest;
import org.siri_hate.main_service.model.dto.response.news.NewsFullResponse;
import org.siri_hate.main_service.model.dto.response.news.NewsSummaryResponse;
import org.siri_hate.main_service.model.entity.News;
import org.siri_hate.main_service.repository.NewsRepository;
import org.siri_hate.main_service.repository.adapters.NewsSpecification;
import org.siri_hate.main_service.service.NewsCategoryService;
import org.siri_hate.main_service.service.NewsService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

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
            @Lazy UserService userService) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.newsCategoryService = newsCategoryService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void createNews(String username, NewsFullRequest news) {
        News newsEntity = newsMapper.toNews(news);
        newsEntity.setUser(userService.findOrCreateUser(username));
        newsEntity.setPublicationDate(LocalDate.now());
        newsEntity.setCategory(
                newsCategoryService.getNewsCategoryEntityById(news.getCategory().getId()));
        newsEntity.setModerationPassed(false);
        newsRepository.save(newsEntity);
    }

    @Override
    public NewsFullResponse getNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isEmpty()) {
            throw new NoSuchElementException("News not found");
        }
        return newsMapper.toNewsFullResponse(news.get());
    }

    @Override
    public Page<NewsSummaryResponse> getNewsByCategoryAndSearchQuery(
            String category, String query, Pageable pageable) {
        Specification<News> spec =
                NewsSpecification.titleStartsWith(query)
                        .and(NewsSpecification.hasCategory(category));
        Page<News> news = newsRepository.findAll(spec, pageable);
        if (news.isEmpty()) {
            throw new RuntimeException("No news found");
        }
        return newsMapper.toNewsSummaryResponsePage(news);
    }

    @Override
    @Transactional
    public Page<NewsSummaryResponse> getModeratedNews(String category, String query,
                                                      Pageable pageable) {
        Specification<News> spec = NewsSpecification.titleStartsWith(query)
                .and(NewsSpecification.hasCategory(category))
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(
                        root.get("moderationPassed")));
        Page<News> news = newsRepository.findAll(spec, pageable);
        if (news.isEmpty()) {
            throw new NoSuchElementException("No moderated news found");
        }
        return newsMapper.toNewsSummaryResponsePage(news);
    }

    @Override
    @Transactional
    public Page<NewsSummaryResponse> getUnmoderatedNews(String category, String query,
                                                        Pageable pageable) {
        Specification<News> spec = NewsSpecification.titleStartsWith(query)
                .and(NewsSpecification.hasCategory(category))
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isFalse(
                        root.get("moderationPassed")));
        Page<News> news = newsRepository.findAll(spec, pageable);
        if (news.isEmpty()) {
            throw new NoSuchElementException("No unmoderated news found");
        }
        return newsMapper.toNewsSummaryResponsePage(news);
    }

    @Override
    public Page<NewsSummaryResponse> getNewsByUser(String username, String query, Pageable pageable) {
        Specification<News> spec =
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (query != null && !query.trim().isEmpty()) {
            spec = spec.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")),
                            query.toLowerCase() + "%"));
        }

        spec = spec.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("username"), username));

        Page<News> news = newsRepository.findAll(spec, pageable);
        if (news.isEmpty()) {
            throw new RuntimeException("No news found for user: " + username);
        }
        return newsMapper.toNewsSummaryResponsePage(news);
    }

    @Override
    @Transactional
    public void updateNews(Long id, NewsFullRequest newsFullRequest) {
        News existingNews =
                newsRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("News with id = " + id + " not found"));

        newsMapper.newsUpdate(newsFullRequest, existingNews);
        newsRepository.save(existingNews);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isEmpty()) {
            throw new NoSuchElementException("News with id = " + id + " not found");
        }
        newsRepository.delete(news.get());
    }

    @Override
    @Transactional
    public void updateNewsModerationStatus(Long id, Boolean moderationPassed) {
        News news =
                newsRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("News with id = " + id + " not found"));
        news.setModerationPassed(moderationPassed);
        newsRepository.save(news);
    }
}
