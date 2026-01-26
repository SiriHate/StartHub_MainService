package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.ProjectCategoryFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectCategoryRequestDTO;
import org.siri_hate.main_service.dto.ProjectCategorySummaryResponseDTO;
import org.siri_hate.main_service.model.entity.project.ProjectCategory;

import java.util.List;

public interface ProjectCategoryService {
    ProjectCategoryFullResponseDTO createProjectCategory(ProjectCategoryRequestDTO request);

    List<ProjectCategorySummaryResponseDTO> getProjectCategories();

    ProjectCategoryFullResponseDTO getProjectCategory(Long id);

    ProjectCategoryFullResponseDTO updateProjectCategory(Long id, ProjectCategoryRequestDTO request);

    void deleteProjectCategory(Long id);

    ProjectCategory getProjectCategoryEntityById(Long id);
}
