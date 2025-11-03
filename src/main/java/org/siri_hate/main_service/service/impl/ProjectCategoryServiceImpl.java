package org.siri_hate.main_service.service.impl;

import jakarta.transaction.Transactional;
import org.siri_hate.main_service.model.dto.mapper.ProjectCategoryMapper;
import org.siri_hate.main_service.model.dto.request.category.ProjectCategoryRequest;
import org.siri_hate.main_service.model.dto.response.category.ProjectCategoryFullResponse;
import org.siri_hate.main_service.model.dto.response.category.ProjectCategorySummaryResponse;
import org.siri_hate.main_service.model.entity.category.ProjectCategory;
import org.siri_hate.main_service.repository.ProjectCategoryRepository;
import org.siri_hate.main_service.service.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCategoryServiceImpl implements ProjectCategoryService {

    private final ProjectCategoryRepository projectCategoryRepository;
    private final ProjectCategoryMapper projectCategoryMapper;

    @Autowired
    public ProjectCategoryServiceImpl(
            ProjectCategoryRepository projectCategoryRepository,
            ProjectCategoryMapper projectCategoryMapper) {
        this.projectCategoryRepository = projectCategoryRepository;
        this.projectCategoryMapper = projectCategoryMapper;
    }

    @Override
    @Transactional
    public void createProjectCategory(ProjectCategoryRequest request) {
        ProjectCategory projectCategoryEntity = projectCategoryMapper.toProjectCategory(request);
        projectCategoryRepository.save(projectCategoryEntity);
    }

    @Override
    public List<ProjectCategorySummaryResponse> getAllProjectCategory() {
        List<ProjectCategory> projectCategories = projectCategoryRepository.findAll();
        if (projectCategories.isEmpty()) {
            throw new RuntimeException("No project categories found!");
        }
        return projectCategoryMapper.toProjectCategorySummaryResponseList(projectCategories);
    }

    @Override
    public ProjectCategoryFullResponse getProjectCategoryById(Long id) {
        ProjectCategory projectCategory =
                projectCategoryRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("No project category with id: " + id));
        return projectCategoryMapper.toProjectCategoryFullResponse(projectCategory);
    }

    @Override
    public ProjectCategory getProjectCategoryEntityById(Long id) {
        return projectCategoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("No project category with id: " + id));
    }

    @Override
    @Transactional
    public void updateProjectCategory(Long id, ProjectCategoryRequest request) {
        ProjectCategory projectCategory =
                projectCategoryRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("No project category with id: " + id));
        projectCategoryMapper.updateProjectCategoryFromRequest(request, projectCategory);
        projectCategoryRepository.save(projectCategory);
    }

    @Override
    @Transactional
    public void deleteProjectCategory(Long id) {
        ProjectCategory projectCategory =
                projectCategoryRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("No project category with id: " + id));
        projectCategoryRepository.delete(projectCategory);
    }
}
