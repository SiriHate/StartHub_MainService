package org.siri_hate.main_service.controller;

import org.siri_hate.main_service.model.dto.response.article.ArticleSummaryResponse;
import org.siri_hate.main_service.model.dto.response.news.NewsSummaryResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectSummaryResponse;
import org.siri_hate.main_service.service.UserService;
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
@RequestMapping("/api/v1/main_service/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my/projects/owned")
    public ResponseEntity<Page<ProjectSummaryResponse>> getProjectsAsOwner(@RequestParam(required = false) String query, @PageableDefault() Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<ProjectSummaryResponse> projects = userService.getProjectsAsOwner(username, query, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/my/projects/member")
    public ResponseEntity<Page<ProjectSummaryResponse>> getProjectsAsMember(@RequestParam(required = false) String query, @PageableDefault() Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<ProjectSummaryResponse> projects = userService.getProjectsAsMember(username, query, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/my/articles")
    public ResponseEntity<Page<ArticleSummaryResponse>> getMyArticles(@RequestParam(required = false) String query, @PageableDefault() Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<ArticleSummaryResponse> articles = userService.getMyArticles(username, query, pageable);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/my/news")
    public ResponseEntity<Page<NewsSummaryResponse>> getMyNews(@RequestParam(required = false) String query, @PageableDefault() Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<NewsSummaryResponse> news = userService.getMyNews(username, query, pageable);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
