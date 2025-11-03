package org.siri_hate.main_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.siri_hate.main_service.model.dto.request.survey.ProjectSurveyRequest;
import org.siri_hate.main_service.model.dto.response.survey.ProjectSurveyResponse;
import org.siri_hate.main_service.model.entity.survey.ProjectSurvey;
import org.siri_hate.main_service.model.entity.survey.SurveyQuestion;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectSurveyMapper {

    @Mapping(target = "questions", source = "questions", qualifiedByName = "mapQuestions")
    ProjectSurvey toProjectSurvey(ProjectSurveyRequest request);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "questions", source = "questions", qualifiedByName = "mapQuestionResponses")
    ProjectSurveyResponse toProjectSurveyResponse(ProjectSurvey survey);

    @Named("mapQuestions")
    default List<SurveyQuestion> mapQuestions(List<ProjectSurveyRequest.QuestionRequest> questions) {
        List<SurveyQuestion> surveyQuestions = new ArrayList<>();
        questions.forEach(question -> {
            SurveyQuestion surveyQuestion = new SurveyQuestion();
            surveyQuestion.setQuestionText(question.getQuestionText());
            surveyQuestions.add(surveyQuestion);
        });
        return surveyQuestions;
    }

    @Named("mapQuestionResponses")
    default List<ProjectSurveyResponse.QuestionResponse> mapQuestionResponses(List<SurveyQuestion> questions) {
        return questions.stream().map(question -> new ProjectSurveyResponse.QuestionResponse(question.getId(), question.getQuestionText())).toList();
    }
}
