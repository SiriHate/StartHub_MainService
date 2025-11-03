package org.siri_hate.main_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.siri_hate.main_service.model.dto.request.category.ProjectCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.ProjectCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.ProjectCategorySummaryResponse;
import org.siri_hate.main_service.model.entity.category.ProjectCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectCategoryMapper {
    ProjectCategory toProjectCategory(ProjectCategoryRequest projectCategoryRequest);

    ProjectCategoryFullResponse toProjectCategoryFullResponse(ProjectCategory projectCategory);

    List<ProjectCategorySummaryResponse> toProjectCategorySummaryResponseList(List<ProjectCategory> projectCategories);

    ProjectCategory updateProjectCategoryFromRequest(ProjectCategoryRequest request, @MappingTarget ProjectCategory projectCategory);
}