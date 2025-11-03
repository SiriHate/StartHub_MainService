package org.siri_hate.main_service.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import org.siri_hate.main_service.model.dto.mapper.ProjectMapper;
import org.siri_hate.main_service.model.dto.request.project.ProjectFullRequest;
import org.siri_hate.main_service.model.dto.request.project_members.ProjectMemberRequest;
import org.siri_hate.main_service.model.dto.response.project.ProjectFullResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectSummaryResponse;
import org.siri_hate.main_service.model.entity.Project;
import org.siri_hate.main_service.model.entity.ProjectMember;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.repository.ProjectRepository;
import org.siri_hate.main_service.repository.adapters.ProjectSpecification;
import org.siri_hate.main_service.service.ProjectCategoryService;
import org.siri_hate.main_service.service.ProjectService;
import org.siri_hate.main_service.service.ProjectSubscriberService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectCategoryService projectCategoryService;
    private final UserService userService;
    private final ProjectSubscriberService projectSubscriberService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectMapper projectMapper,
                              ProjectCategoryService projectCategoryService,
                              @Lazy UserService userService,
                              ProjectSubscriberService projectSubscriberService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectCategoryService = projectCategoryService;
        this.userService = userService;
        this.projectSubscriberService = projectSubscriberService;
    }

    @Override
    @Transactional
    public void createProject(String username, ProjectFullRequest project) {
        Project projectEntity = projectMapper.toProject(project);
        projectEntity.setUser(userService.findOrCreateUser(username));
        projectEntity.setCategory(projectCategoryService.getProjectCategoryEntityById(project.getCategory().getId()));
        projectEntity.setModerationPassed(false);

        Set<ProjectMember> projectMembers = new HashSet<>();
        for (ProjectMemberRequest memberRequest : project.getMembers()) {
            User memberUser = userService.findOrCreateUser(memberRequest.getUsername());
            ProjectMember projectMember = new ProjectMember();
            projectMember.setUser(memberUser);
            projectMember.setRole(memberRequest.getRole());
            projectMember.setProject(projectEntity);
            projectMembers.add(projectMember);
        }
        projectEntity.setMembers(projectMembers);

        projectRepository.save(projectEntity);
    }

    @Override
    public Page<ProjectSummaryResponse> getProjectsByCategoryAndSearchQuery(String category, String query, Pageable pageable) {
        List<Specification<Project>> specs = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            specs.add(ProjectSpecification.projectNameStartsWith(query));
        }
        if (category != null && !category.isBlank()) {
            specs.add(ProjectSpecification.hasCategory(category));
        }

        Specification<Project> spec = Specification.allOf(specs.toArray(new Specification[0]));
        Page<Project> projects = projectRepository.findAll(spec, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found");
        }
        return projectMapper.toProjectSummaryResponsePage(projects);
    }

    @Override
    @Transactional
    public ProjectFullResponse getProjectInfoById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new RuntimeException("No project found with id " + id);
        }
        return projectMapper.toProjectFullResponse(project.get());
    }

    @Override
    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new RuntimeException("No project found with id " + id);
        }
        return project.get();
    }

    @Override
    @Transactional
    public void updateProject(ProjectFullRequest request, Long id) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No project with id " + id + " found"));

        existingProject.setProjectName(request.getProjectName());
        existingProject.setProjectDescription(request.getProjectDescription());
        existingProject.setProjectLogoUrl(request.getProjectLogoUrl());
        existingProject.setCategory(projectCategoryService.getProjectCategoryEntityById(request.getCategory().getId()));

        existingProject.getMembers().clear();
        for (ProjectMemberRequest memberRequest : request.getMembers()) {
            User memberUser = userService.findOrCreateUser(memberRequest.getUsername());
            ProjectMember projectMember = new ProjectMember();
            projectMember.setUser(memberUser);
            projectMember.setProject(existingProject);
            projectMember.setRole(memberRequest.getRole());
            existingProject.getMembers().add(projectMember);
        }

        projectRepository.save(existingProject);
        projectSubscriberService.notifySubscribersAboutUpdate(id, existingProject.getProjectName());
    }

    @Override
    @Transactional
    public void deleteProjectById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isEmpty()) {
            throw new RuntimeException("No project with id " + id + " found");
        }
        projectRepository.delete(projectOptional.get());
    }

    @Override
    @Transactional
    public boolean toggleProjectLike(String username, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("No project with id " + projectId + " found"));

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

    @Override
    public Long getProjectLikesCount(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("No project with id " + projectId + " found"));
        return (long) project.getProjectLikes().size();
    }

    @Override
    public Page<ProjectSummaryResponse> getModeratedProjects(String category, String query, Pageable pageable) {
        List<Specification<Project>> specs = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            specs.add(ProjectSpecification.projectNameStartsWith(query));
        }
        if (category != null && !category.isBlank()) {
            specs.add(ProjectSpecification.hasCategory(category));
        }
        specs.add((root, cq, cb) -> cb.isTrue(root.get("moderationPassed")));

        Specification<Project> spec = Specification.allOf(specs.toArray(new Specification[0]));
        Page<Project> projects = projectRepository.findAll(spec, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No moderated projects found");
        }
        return projectMapper.toProjectSummaryResponsePage(projects);
    }

    @Override
    public Page<ProjectSummaryResponse> getUnmoderatedProjects(String category, String query, Pageable pageable) {
        List<Specification<Project>> specs = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            specs.add(ProjectSpecification.projectNameStartsWith(query));
        }
        if (category != null && !category.isBlank()) {
            specs.add(ProjectSpecification.hasCategory(category));
        }
        specs.add((root, cq, cb) -> cb.isFalse(root.get("moderationPassed")));

        Specification<Project> spec = Specification.allOf(specs.toArray(new Specification[0]));
        Page<Project> projects = projectRepository.findAll(spec, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No unmoderated projects found");
        }
        return projectMapper.toProjectSummaryResponsePage(projects);
    }

    @Override
    @Transactional
    public void updateProjectModerationStatus(Long projectId, Boolean moderationPassed) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("No project with id " + projectId + " found"));
        project.setModerationPassed(moderationPassed);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public Page<ProjectSummaryResponse> getProjectsByOwner(String username, String query, Pageable pageable) {
        List<Specification<Project>> specs = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            specs.add((root, cq, cb) ->
                    cb.like(cb.lower(root.get("projectName")), query.toLowerCase() + "%"));
        }

        specs.add((root, cq, cb) -> cb.equal(root.get("user").get("username"), username));

        Specification<Project> spec = Specification.allOf(specs.toArray(new Specification[0]));
        Page<Project> projects = projectRepository.findAll(spec, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found for user: " + username);
        }
        return projectMapper.toProjectSummaryResponsePage(projects);
    }

    @Override
    @Transactional
    public Page<ProjectSummaryResponse> getProjectsByMember(String username, String query, Pageable pageable) {
        List<Specification<Project>> specs = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            specs.add((root, cq, cb) ->
                    cb.like(cb.lower(root.get("projectName")), query.toLowerCase() + "%"));
        }

        specs.add((root, cq, cb) -> {
            Join<Project, ProjectMember> memberJoin = root.join("members");
            return cb.equal(memberJoin.get("user").get("username"), username);
        });

        Specification<Project> spec = Specification.allOf(specs.toArray(new Specification[0]));
        Page<Project> projects = projectRepository.findAll(spec, pageable);
        if (projects.isEmpty()) {
            throw new RuntimeException("No projects found for user: " + username);
        }
        return projectMapper.toProjectSummaryResponsePage(projects);
    }
}
