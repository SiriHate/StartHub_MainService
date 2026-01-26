package org.siri_hate.main_service.controller;

import org.siri_hate.main_service.api.ProjectSurveyApi;
import org.siri_hate.main_service.dto.ProjectSurveyFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectSurveyRequestDTO;
import org.siri_hate.main_service.dto.SurveySubmissionFullResponseDTO;
import org.siri_hate.main_service.dto.SurveySubmissionRequestDTO;
import org.siri_hate.main_service.service.ProjectSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectSurveyController implements ProjectSurveyApi {

    private final ProjectSurveyService projectSurveyService;

    @Autowired
    public ProjectSurveyController(ProjectSurveyService projectSurveyService) {
        this.projectSurveyService = projectSurveyService;
    }

    @Override
    public ResponseEntity<ProjectSurveyFullResponseDTO> createProjectSurvey(Long projectId, ProjectSurveyRequestDTO projectSurveyRequestDTO) {
        var response = projectSurveyService.createSurvey(projectId, projectSurveyRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteProjectSurvey(Long projectId) {
        projectSurveyService.deleteSurvey(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<SurveySubmissionFullResponseDTO>> getProjectSurveySubmissions(Long projectId, String sort) {
        var response = projectSurveyService.getAllSurveySubmissions(projectId, sort);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectSurveyFullResponseDTO> getProjectSurvey(Long projectId) {
        var response = projectSurveyService.getSurvey(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SurveySubmissionFullResponseDTO> submitProjectSurveyAnswers(Long projectId, SurveySubmissionRequestDTO surveySubmissionRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var response = projectSurveyService.submitSurveyAnswers(username, projectId, surveySubmissionRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}