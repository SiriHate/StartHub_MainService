package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.siri_hate.main_service.dto.ProjectCategoryFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectCategoryRequestDTO;
import org.siri_hate.main_service.dto.ProjectCategorySummaryResponseDTO;
import org.siri_hate.main_service.model.mapper.ProjectCategoryMapper;
import org.siri_hate.main_service.model.entity.project.ProjectCategory;
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
    public ProjectCategoryFullResponseDTO createProjectCategory(ProjectCategoryRequestDTO request) {
        ProjectCategory category = projectCategoryMapper.toProjectCategory(request);
        projectCategoryRepository.save(category);
        return projectCategoryMapper.toProjectCategoryFullResponse(category);
    }

    @Override
    public List<ProjectCategorySummaryResponseDTO> getProjectCategories() {
        List<ProjectCategory> category = projectCategoryRepository.findAll();
        if (category.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return projectCategoryMapper.toProjectCategoriesSummaryResponse(category);
    }

    @Override
    public ProjectCategoryFullResponseDTO getProjectCategory(Long id) {
        ProjectCategory category = projectCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return projectCategoryMapper.toProjectCategoryFullResponse(category);
    }

    @Override
    public ProjectCategory getProjectCategoryEntityById(Long id) {
        return projectCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public ProjectCategoryFullResponseDTO updateProjectCategory(Long id, ProjectCategoryRequestDTO request) {
        ProjectCategory category = projectCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        category = projectCategoryMapper.updateProjectCategoryFromRequest(request, category);
        projectCategoryRepository.save(category);
        return projectCategoryMapper.toProjectCategoryFullResponse(category);
    }

    @Override
    @Transactional
    public void deleteProjectCategory(Long id) {
        ProjectCategory category = projectCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        projectCategoryRepository.delete(category);
    }
}