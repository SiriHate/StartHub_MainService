package org.siri_hate.main_service.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.siri_hate.main_service.dto.ProjectFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectPageResponseDTO;
import org.siri_hate.main_service.dto.ProjectRequestDTO;
import org.siri_hate.main_service.model.mapper.ProjectMapper;
import org.siri_hate.main_service.model.entity.project.Project;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.repository.ProjectRepository;
import org.siri_hate.main_service.repository.specification.ProjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectCategoryService projectCategoryService;
    private final UserService userService;
    private final ProjectSubscriberService projectSubscriberService;
    private final FileService fileService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          ProjectMapper projectMapper,
                          ProjectCategoryService projectCategoryService,
                          UserService userService,
                          ProjectSubscriberService projectSubscriberService,
                          FileService fileService
    )
    {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectCategoryService = projectCategoryService;
        this.userService = userService;
        this.projectSubscriberService = projectSubscriberService;
        this.fileService = fileService;
    }

    @Transactional
    public ProjectFullResponseDTO createProject(String username, ProjectRequestDTO request, MultipartFile logo) {
        Project project = projectMapper.toProject(request);
        project.setOwner(userService.findOrCreateUser(username));
        project.setCategory(projectCategoryService.getProjectCategoryEntityById(request.getCategoryId()));
        String imageKey = fileService.uploadProjectLogo(logo);
        project.setImageKey(imageKey);
        projectRepository.save(project);
        return projectMapper.toProjectFullResponse(project);
    }

    public ProjectPageResponseDTO getProjects(String category, String query, int page, int size, boolean isModerationPassed) {
        Specification<Project> specification = Specification.allOf(
                ProjectSpecification.projectNameStartsWith(query),
                ProjectSpecification.hasCategory(category),
                ProjectSpecification.moderationPassed(isModerationPassed)
        );
        Page<Project> projects = projectRepository.findAll(specification, PageRequest.of(page, size));
        if (projects.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return projectMapper.toProjectPageResponse(projects);
    }

    public ProjectPageResponseDTO getMyProjects(String username, String query, String userRole, int page, int size) {
        return null;
    }

    @Transactional
    public ProjectFullResponseDTO getProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return projectMapper.toProjectFullResponse(project);
    }

    public Project getProjectEntity(Long id) {
        return projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public ProjectFullResponseDTO updateProject(Long id, ProjectRequestDTO request, MultipartFile logo) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        project.setCategory(projectCategoryService.getProjectCategoryEntityById(request.getCategoryId()));
        String imageKey = fileService.uploadProjectLogo(logo);
        project.setImageKey(imageKey);
        projectRepository.save(project);
        projectSubscriberService.notifySubscribersAboutUpdate(id, project.getName());
        return projectMapper.toProjectFullResponse(project);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    public boolean toggleProjectLike(String username, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        User user = userService.findOrCreateUser(username);
        boolean alreadyLiked = project.getProjectLikes()
                .stream()
                .anyMatch(like -> like.getUser().getId().equals(user.getId()));
        if (alreadyLiked) {
            project.getProjectLikes().removeIf(like -> like.getUser().getId().equals(user.getId()));
            projectRepository.save(project);
            return false;
        } else {
            project.addLike(user);
            projectRepository.save(project);
            return true;
        }
    }

    public long getProjectLikesCount(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        return project.getProjectLikes().size();
    }

    @Transactional
    public void updateProjectModerationStatus(Long projectId, Boolean moderationPassed) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        project.setModerationPassed(moderationPassed);
        projectRepository.save(project);
    }
}
