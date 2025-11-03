package org.siri_hate.main_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.siri_hate.main_service.model.dto.request.project.ProjectFullRequest;
import org.siri_hate.main_service.model.dto.response.project.ProjectFullResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectSummaryResponse;
import org.siri_hate.main_service.service.ProjectService;
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
@RequestMapping("/api/v1/main_service/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody @Valid ProjectFullRequest project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        projectService.createProject(username, project);
        return new ResponseEntity<>("Project has been successfully created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectSummaryResponse>> getAllProjects(@RequestParam(required = false) String category,
                                                                       @RequestParam(required = false) String query,
                                                                       @RequestParam(required = false) Boolean moderationPassed,
                                                                       @PageableDefault() Pageable pageable) {
        Page<ProjectSummaryResponse> projects;
        if (moderationPassed != null) {
            projects = moderationPassed ? projectService.getModeratedProjects(category, query, pageable) : projectService.getUnmoderatedProjects(category, query, pageable);
        } else {
            projects = projectService.getProjectsByCategoryAndSearchQuery(category, query, pageable);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectFullResponse> getProjectById(@PathVariable @Positive Long id) {
        ProjectFullResponse project = projectService.getProjectInfoById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable @Positive Long id, @RequestBody @Valid ProjectFullRequest project) {
        projectService.updateProject(project, id);
        return new ResponseEntity<>("Project has been successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable @Positive Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>("Project was successfully deleted", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<Boolean> toggleProjectLike(@PathVariable @Positive Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isLiked = projectService.toggleProjectLike(username, id);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }

    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> getProjectLikesCount(@PathVariable @Positive Long id) {
        Long likesCount = projectService.getProjectLikesCount(id);
        return new ResponseEntity<>(likesCount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/moderationPassed")
    public ResponseEntity<String> updateProjectModerationStatus(@PathVariable @Positive Long id, @RequestBody Boolean moderationPassed) {
        projectService.updateProjectModerationStatus(id, moderationPassed);
        return new ResponseEntity<>("Project moderation status has been successfully updated", HttpStatus.OK);
    }
}
