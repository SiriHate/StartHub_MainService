package org.siri_hate.main_service.controller;

import org.jspecify.annotations.Nullable;
import org.siri_hate.main_service.api.ProjectApi;
import org.siri_hate.main_service.dto.ProjectFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectPageResponseDTO;
import org.siri_hate.main_service.dto.ProjectRequestDTO;
import org.siri_hate.main_service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProjectController implements ProjectApi {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<ProjectFullResponseDTO> createProject(ProjectRequestDTO project, MultipartFile logo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerUsername = authentication.getName();
        var response = projectService.createProject(ownerUsername, project, logo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProject(Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ProjectPageResponseDTO> getMyProjects(Integer page, Integer size, @Nullable String role, @Nullable String query) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        var response = projectService.getMyProjects(query, username, role, page, size);
//         return new ResponseEntity<>(response, HttpStatus.OK);
        return null;
    }

    @Override
    public ResponseEntity<ProjectFullResponseDTO> getProject(Long id) {
        var response = projectService.getProject(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> getProjectLikesCount(Long id) {
        var response = projectService.getProjectLikesCount(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectPageResponseDTO> getProjects(Integer page, Integer size, String category, String query, Boolean moderationPassed) {
        var response = projectService.getProjects(category, query, page, size, moderationPassed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> toggleProjectLike(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        projectService.toggleProjectLike(username, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectFullResponseDTO> updateProject(Long id, ProjectRequestDTO project, MultipartFile logo) {
        var response = projectService.updateProject(id, project, logo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateProjectModerationPassed(Long id, Boolean body) {
        projectService.updateProjectModerationStatus(id, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateProjectModerationStatus(Long id, Boolean body) {
        projectService.updateProjectModerationStatus(id, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
