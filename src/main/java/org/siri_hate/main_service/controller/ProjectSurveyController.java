package org.siri_hate.main_service.controller;

import jakarta.validation.Valid;
import org.siri_hate.main_service.model.dto.request.survey.ProjectSurveyRequest;
import org.siri_hate.main_service.model.dto.request.survey.SurveySubmissionRequest;
import org.siri_hate.main_service.model.dto.response.survey.ProjectSurveyResponse;
import org.siri_hate.main_service.model.dto.response.survey.SurveySubmissionResponse;
import org.siri_hate.main_service.service.ProjectSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/main_service/projects/{projectId}/surveys")
public class ProjectSurveyController {

    private final ProjectSurveyService projectSurveyService;

    @Autowired
    public ProjectSurveyController(ProjectSurveyService projectSurveyService) {
        this.projectSurveyService = projectSurveyService;
    }

    @PostMapping
    public ResponseEntity<ProjectSurveyResponse> createSurvey(@PathVariable Long projectId, @Valid @RequestBody ProjectSurveyRequest request) {
        ProjectSurveyResponse response = projectSurveyService.createSurvey(projectId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProjectSurveyResponse> getSurvey(@PathVariable Long projectId) {
        ProjectSurveyResponse response = projectSurveyService.getSurvey(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long projectId) {
        projectSurveyService.deleteSurvey(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/submissions")
    public ResponseEntity<SurveySubmissionResponse> submitSurveyAnswers(@PathVariable Long projectId, @Valid @RequestBody SurveySubmissionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SurveySubmissionResponse response = projectSurveyService.submitSurveyAnswers(authentication.getName(), projectId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/submissions")
    public ResponseEntity<List<SurveySubmissionResponse>> getAllSurveySubmissions(@PathVariable Long projectId, @RequestParam(required = false) String sort) {
        List<SurveySubmissionResponse> responses = projectSurveyService.getAllSurveySubmissions(projectId, sort);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
