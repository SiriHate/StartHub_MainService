package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.siri_hate.main_service.dto.ProjectSurveyFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectSurveyRequestDTO;
import org.siri_hate.main_service.dto.SurveySubmissionFullResponseDTO;
import org.siri_hate.main_service.dto.SurveySubmissionRequestDTO;
import org.siri_hate.main_service.model.mapper.ProjectSurveyMapper;
import org.siri_hate.main_service.model.entity.project.Project;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.model.entity.survey.ProjectSurvey;
import org.siri_hate.main_service.model.entity.survey.SurveySubmission;
import org.siri_hate.main_service.model.mapper.SurveySubmissionMapper;
import org.siri_hate.main_service.repository.ProjectSurveyRepository;
import org.siri_hate.main_service.repository.SurveySubmissionRepository;
import org.siri_hate.main_service.service.ProjectService;
import org.siri_hate.main_service.service.ProjectSurveyService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectSurveyImpl implements ProjectSurveyService {

    private final ProjectSurveyRepository projectSurveyRepository;
    private final ProjectService projectService;
    private final ProjectSurveyMapper projectSurveyMapper;
    private final SurveySubmissionRepository surveySubmissionRepository;
    private final UserService userService;
    private final SurveySubmissionMapper surveySubmissionMapper;

    @Autowired
    public ProjectSurveyImpl(
            ProjectSurveyRepository projectSurveyRepository,
            ProjectService projectService,
            ProjectSurveyMapper projectSurveyMapper,
            SurveySubmissionRepository surveySubmissionRepository,
            UserService userService,
            SurveySubmissionMapper surveySubmissionMapper
    )
    {
        this.projectSurveyRepository = projectSurveyRepository;
        this.projectService = projectService;
        this.projectSurveyMapper = projectSurveyMapper;
        this.surveySubmissionRepository = surveySubmissionRepository;
        this.userService = userService;
        this.surveySubmissionMapper = surveySubmissionMapper;
    }

    @Override
    @Transactional
    public ProjectSurveyFullResponseDTO createSurvey(Long projectId, ProjectSurveyRequestDTO request) {
        Project project = projectService.getProjectEntity(projectId);
        if (project.getSurvey() != null) {
            throw new EntityExistsException("Survey already exists for this project");
        }
        ProjectSurvey survey = projectSurveyMapper.toProjectSurvey(request);
        survey.setProject(project);
        project.setSurvey(survey);
        survey.getQuestions().forEach(question -> question.setSurvey(survey));
        ProjectSurvey savedSurvey = projectSurveyRepository.save(survey);
        projectService.updateProject(project);
        return projectSurveyMapper.toProjectSurveyFullResponseDTO(savedSurvey);
    }

    @Override
    @Transactional
    public ProjectSurveyFullResponseDTO getSurvey(Long projectId) {
        Project project = projectService.getProjectEntity(projectId);
        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException();
        }
        return projectSurveyMapper.toProjectSurveyFullResponseDTO(survey);
    }

    @Override
    @Transactional
    public void deleteSurvey(Long projectId) {
        Project project = projectService.getProjectEntity(projectId);
        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException();
        }
        List<SurveySubmission> submissions = surveySubmissionRepository.findBySurvey(survey);
        surveySubmissionRepository.deleteAll(submissions);
        project.setSurvey(null);
        projectService.updateProject(project);
        projectSurveyRepository.delete(survey);
    }

    @Override
    @Transactional
    public SurveySubmissionFullResponseDTO submitSurveyAnswers(
            String username,
            Long projectId,
            SurveySubmissionRequestDTO request
    )
    {
        Project project = projectService.getProjectEntity(projectId);
        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException();
        }
        User user = userService.findOrCreateUser(username);
        boolean hasAlreadySubmitted = surveySubmissionRepository.existsBySurveyAndRespondent(survey, user);
        if (hasAlreadySubmitted) {
            throw new IllegalStateException();
        }
        SurveySubmission submission = new SurveySubmission();
        submission.setSurvey(survey);
        submission.setRespondent(user);
        SurveySubmission savedSubmission = surveySubmissionRepository.save(submission);
        return surveySubmissionMapper.toSurveySubmissionFullResponseDTO(savedSubmission);
    }

    @Override
    @Transactional
    public List<SurveySubmissionFullResponseDTO> getAllSurveySubmissions(Long projectId, String sort) {
        Project project = projectService.getProjectEntity(projectId);
        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException();
        }
        List<SurveySubmission> submissions = surveySubmissionRepository.findBySurvey(survey);
        return surveySubmissionMapper.toSurveySubmissionFullResponseListDTO(submissions);
    }
}