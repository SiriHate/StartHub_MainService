package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.ProjectSurveyFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectSurveyRequestDTO;
import org.siri_hate.main_service.dto.SurveySubmissionFullResponseDTO;
import org.siri_hate.main_service.dto.SurveySubmissionRequestDTO;

import java.util.List;

public interface ProjectSurveyService {
    ProjectSurveyFullResponseDTO createSurvey(Long projectId, ProjectSurveyRequestDTO request);

    ProjectSurveyFullResponseDTO getSurvey(Long projectId);

    void deleteSurvey(Long projectId);

    SurveySubmissionFullResponseDTO submitSurveyAnswers(String username, Long projectId, SurveySubmissionRequestDTO request);

    List<SurveySubmissionFullResponseDTO> getAllSurveySubmissions(Long projectId, String sort);
}