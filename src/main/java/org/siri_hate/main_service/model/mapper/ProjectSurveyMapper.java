package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.ProjectSurveyFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectSurveyRequestDTO;
import org.siri_hate.main_service.model.entity.survey.ProjectSurvey;
import org.siri_hate.main_service.model.entity.survey.SurveyQuestion;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectSurveyMapper {

    ProjectSurvey toProjectSurvey(ProjectSurveyRequestDTO projectSurveyRequestDTO);

    ProjectSurveyFullResponseDTO toProjectSurveyFullResponseDTO(ProjectSurvey projectSurvey);
}