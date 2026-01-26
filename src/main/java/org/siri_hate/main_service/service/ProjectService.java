package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.ProjectFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectPageResponseDTO;
import org.siri_hate.main_service.dto.ProjectRequestDTO;
import org.siri_hate.main_service.model.entity.project.Project;

public interface ProjectService {
    ProjectFullResponseDTO createProject(String ownerUsername, ProjectRequestDTO request);

    ProjectPageResponseDTO getProjects(
            String category,
            String query,
            int page,
            int size,
            boolean isModerationPassed
    );

    ProjectFullResponseDTO getProject(Long id);

    Project getProjectEntity(Long id);

    ProjectFullResponseDTO updateProject( Long id, ProjectRequestDTO request);

    Project updateProject(Project project);

    void deleteProject(Long id);

    boolean toggleProjectLike(String username, Long projectId);

    long getProjectLikesCount(Long projectId);

    void updateProjectModerationStatus(Long projectId, Boolean moderationPassed);
}