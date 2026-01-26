package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.SurveySubmissionFullResponseDTO;
import org.siri_hate.main_service.dto.SurveySubmissionRequestDTO;
import org.siri_hate.main_service.model.entity.survey.SurveySubmission;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SurveySubmissionMapper {
    SurveySubmission toSurveySubmission(SurveySubmissionRequestDTO request);

    SurveySubmissionFullResponseDTO toSurveySubmissionFullResponseDTO(SurveySubmission surveySubmission);

    List<SurveySubmissionFullResponseDTO> toSurveySubmissionFullResponseListDTO(List<SurveySubmission> surveySubmissions);
}
