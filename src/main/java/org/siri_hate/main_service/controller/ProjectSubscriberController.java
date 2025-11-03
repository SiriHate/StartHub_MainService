package org.siri_hate.main_service.controller;

import org.siri_hate.main_service.service.ProjectSubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/main_service/projects")
public class ProjectSubscriberController {

    private final ProjectSubscriberService projectSubscriberService;

    @Autowired
    public ProjectSubscriberController(ProjectSubscriberService projectSubscriberService) {
        this.projectSubscriberService = projectSubscriberService;
    }

    @PostMapping("/{projectId}/subscriptions")
    public ResponseEntity<Void> subscribeToProject(@PathVariable Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        projectSubscriberService.subscribeToProject(projectId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/subscriptions")
    public ResponseEntity<Void> unsubscribeFromProject(@PathVariable Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        projectSubscriberService.unsubscribeFromProject(projectId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectId}/subscriptions")
    public ResponseEntity<Boolean> isUserSubscribed(@PathVariable Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isSubscribed = projectSubscriberService.isUserSubscribed(projectId, authentication.getName());
        return new ResponseEntity<>(isSubscribed, HttpStatus.OK);
    }
}
