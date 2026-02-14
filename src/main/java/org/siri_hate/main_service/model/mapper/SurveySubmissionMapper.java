package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.SurveySubmissionFullResponseDTO;
import org.siri_hate.main_service.dto.SurveySubmissionRequestDTO;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.model.entity.project.survey.ProjectSurvey;
import org.siri_hate.main_service.model.entity.project.survey.SurveySubmission;
import org.siri_hate.main_service.model.mapper.utils.UserResolver;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                UserResolver.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SurveySubmissionMapper {

    @Mapping(target = "id", ignore = true)
    SurveySubmission toSurveySubmission(User respondent, ProjectSurvey survey, SurveySubmissionRequestDTO request);

    @Mapping(target = "respondentUsername", source = "respondent", qualifiedByName = "toUsername")
    SurveySubmissionFullResponseDTO toSurveySubmissionFullResponseDTO(SurveySubmission surveySubmission);

    List<SurveySubmissionFullResponseDTO> toSurveySubmissionFullResponseListDTO(List<SurveySubmission> surveySubmissions);
}
