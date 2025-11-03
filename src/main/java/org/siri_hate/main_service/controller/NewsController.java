package org.siri_hate.main_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.siri_hate.main_service.model.dto.request.news.NewsFullRequest;
import org.siri_hate.main_service.model.dto.response.news.NewsFullResponse;
import org.siri_hate.main_service.model.dto.response.news.NewsSummaryResponse;
import org.siri_hate.main_service.service.NewsService;
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
@RequestMapping("/api/v1/main_service/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<String> createNews(@Valid @RequestBody NewsFullRequest news) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        newsService.createNews(username, news);
        return new ResponseEntity<>("News was successfully created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<NewsSummaryResponse>> getAllNews(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean moderationPassed,
            @PageableDefault() Pageable pageable) {
        Page<NewsSummaryResponse> news;
        if (moderationPassed != null) {
            news = moderationPassed ?
                    newsService.getModeratedNews(category, query, pageable) :
                    newsService.getUnmoderatedNews(category, query, pageable);
        } else {
            news = newsService.getNewsByCategoryAndSearchQuery(category, query, pageable);
        }
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsFullResponse> getNewsById(@PathVariable Long id) {
        NewsFullResponse news = newsService.getNewsById(id);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateNews(
            @PathVariable Long id, @Valid @RequestBody NewsFullRequest news) {
        newsService.updateNews(id, news);
        return new ResponseEntity<>("News has been successfully modified", HttpStatus.OK);
    }

    @PatchMapping("/{id}/moderationPassed")
    public ResponseEntity<String> updateNewsModerationStatus(
            @PathVariable @Positive Long id,
            @RequestBody Boolean moderationPassed) {
        newsService.updateNewsModerationStatus(id, moderationPassed);
        return new ResponseEntity<>("News moderation status has been successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return new ResponseEntity<>("News has been successfully deleted", HttpStatus.NO_CONTENT);
    }
}
