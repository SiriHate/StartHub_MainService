package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.ProjectSurveyFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectSurveyRequestDTO;
import org.siri_hate.main_service.model.entity.project.survey.ProjectSurvey;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectSurveyMapper {

    ProjectSurvey toProjectSurvey(ProjectSurveyRequestDTO projectSurveyRequestDTO);

    ProjectSurveyFullResponseDTO toProjectSurveyFullResponseDTO(ProjectSurvey projectSurvey);
}