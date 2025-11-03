package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.dto.request.survey.ProjectSurveyRequest;
import org.siri_hate.main_service.model.dto.request.survey.SurveySubmissionRequest;
import org.siri_hate.main_service.model.dto.response.survey.ProjectSurveyResponse;
import org.siri_hate.main_service.model.dto.response.survey.SurveySubmissionResponse;

import java.util.List;

public interface ProjectSurveyService {
    ProjectSurveyResponse createSurvey(Long projectId, ProjectSurveyRequest request);

    ProjectSurveyResponse getSurvey(Long projectId);

    void deleteSurvey(Long projectId);

    SurveySubmissionResponse submitSurveyAnswers(String username, Long projectId, SurveySubmissionRequest request);

    List<SurveySubmissionResponse> getAllSurveySubmissions(Long projectId, String sort);
}
