package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.siri_hate.main_service.model.dto.mapper.ProjectSurveyMapper;
import org.siri_hate.main_service.model.dto.request.survey.ProjectSurveyRequest;
import org.siri_hate.main_service.model.dto.request.survey.SurveySubmissionRequest;
import org.siri_hate.main_service.model.dto.response.survey.ProjectSurveyResponse;
import org.siri_hate.main_service.model.dto.response.survey.SurveySubmissionResponse;
import org.siri_hate.main_service.model.entity.Project;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.model.entity.survey.ProjectSurvey;
import org.siri_hate.main_service.model.entity.survey.SurveyAnswer;
import org.siri_hate.main_service.model.entity.survey.SurveyQuestion;
import org.siri_hate.main_service.model.entity.survey.SurveySubmission;
import org.siri_hate.main_service.repository.ProjectRepository;
import org.siri_hate.main_service.repository.ProjectSurveyRepository;
import org.siri_hate.main_service.repository.SurveySubmissionRepository;
import org.siri_hate.main_service.repository.UserRepository;
import org.siri_hate.main_service.service.ProjectSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectSurveyImpl implements ProjectSurveyService {

    private final ProjectSurveyRepository projectSurveyRepository;
    private final ProjectRepository projectRepository;
    private final ProjectSurveyMapper projectSurveyMapper;
    private final UserRepository userRepository;
    private final SurveySubmissionRepository surveySubmissionRepository;

    @Autowired
    public ProjectSurveyImpl(
            ProjectSurveyRepository projectSurveyRepository,
            ProjectRepository projectRepository,
            ProjectSurveyMapper projectSurveyMapper,
            UserRepository userRepository,
            SurveySubmissionRepository surveySubmissionRepository) {
        this.projectSurveyRepository = projectSurveyRepository;
        this.projectRepository = projectRepository;
        this.projectSurveyMapper = projectSurveyMapper;
        this.userRepository = userRepository;
        this.surveySubmissionRepository = surveySubmissionRepository;
    }

    @Override
    @Transactional
    public ProjectSurveyResponse createSurvey(Long projectId, ProjectSurveyRequest request) {
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Project not found with id: " + projectId));

        if (project.getSurvey() != null) {
            throw new IllegalStateException("Survey already exists for this project");
        }

        ProjectSurvey survey = projectSurveyMapper.toProjectSurvey(request);
        survey.setProject(project);
        project.setSurvey(survey);

        survey.getQuestions().forEach(question -> question.setSurvey(survey));

        ProjectSurvey savedSurvey = projectSurveyRepository.save(survey);
        projectRepository.save(project);

        return projectSurveyMapper.toProjectSurveyResponse(savedSurvey);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectSurveyResponse getSurvey(Long projectId) {
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException("Survey not found for project with id: " + projectId);
        }

        return projectSurveyMapper.toProjectSurveyResponse(survey);
    }

    @Override
    @Transactional
    public void deleteSurvey(Long projectId) {
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException("Survey not found for project with id: " + projectId);
        }

        List<SurveySubmission> submissions = surveySubmissionRepository.findBySurvey(survey);
        surveySubmissionRepository.deleteAll(submissions);

        project.setSurvey(null);
        projectRepository.save(project);
        projectSurveyRepository.delete(survey);
    }

    @Override
    @Transactional
    public SurveySubmissionResponse submitSurveyAnswers(
            String username, Long projectId, SurveySubmissionRequest request) {
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException("Survey not found for project with id: " + projectId);
        }

        User user =
                userRepository
                        .findUserByUsername(username)
                        .orElseThrow(
                                () -> new EntityNotFoundException("User not found with username: " + username));

        boolean hasAlreadySubmitted =
                surveySubmissionRepository.existsBySurveyAndRespondent(survey, user);
        if (hasAlreadySubmitted) {
            throw new IllegalStateException("User has already submitted answers to this survey");
        }

        SurveySubmission submission = new SurveySubmission();
        submission.setSurvey(survey);
        submission.setRespondent(user);
        submission.setSubmittedAt(LocalDateTime.now());

        List<SurveyAnswer> answers =
                request.getAnswers().stream()
                        .map(
                                answerRequest -> {
                                    SurveyQuestion question =
                                            survey.getQuestions().stream()
                                                    .filter(q -> q.getId().equals(answerRequest.getQuestionId()))
                                                    .findFirst()
                                                    .orElseThrow(
                                                            () ->
                                                                    new EntityNotFoundException(
                                                                            "Question not found with id: "
                                                                                    + answerRequest.getQuestionId()));

                                    SurveyAnswer answer = new SurveyAnswer();
                                    answer.setSubmission(submission);
                                    answer.setQuestion(question);
                                    answer.setResponseText(answerRequest.getAnswerText());
                                    return answer;
                                })
                        .collect(Collectors.toList());

        submission.setResponses(answers);
        SurveySubmission savedSubmission = surveySubmissionRepository.save(submission);

        return mapToSubmissionResponse(savedSubmission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveySubmissionResponse> getAllSurveySubmissions(Long projectId, String sort) {
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectSurvey survey = project.getSurvey();
        if (survey == null) {
            throw new EntityNotFoundException("Survey not found for project with id: " + projectId);
        }

        List<SurveySubmission> submissions = surveySubmissionRepository.findBySurvey(survey);
        List<SurveySubmissionResponse> responses =
                submissions.stream().map(this::mapToSubmissionResponse).collect(Collectors.toList());

        if (sort != null) {
            if (sort.equalsIgnoreCase("asc")) {
                responses.sort(Comparator.comparing(SurveySubmissionResponse::getSubmittedAt));
            } else if (sort.equalsIgnoreCase("desc")) {
                responses.sort((a, b) -> b.getSubmittedAt().compareTo(a.getSubmittedAt()));
            }
        }

        return responses;
    }

    private SurveySubmissionResponse mapToSubmissionResponse(SurveySubmission submission) {
        SurveySubmissionResponse response = new SurveySubmissionResponse();
        response.setId(submission.getId());
        response.setSurveyId(submission.getSurvey().getId());
        response.setRespondentUsername(submission.getRespondent().getUsername());
        response.setSubmittedAt(submission.getSubmittedAt());
        response.setAverageRating(submission.getAverageRating());

        response.setAnswers(
                submission.getResponses().stream()
                        .map(
                                answer ->
                                        new SurveySubmissionResponse.AnswerResponse(
                                                answer.getId(),
                                                answer.getQuestion().getQuestionText(),
                                                answer.getResponseText()))
                        .collect(Collectors.toList()));

        return response;
    }
}
