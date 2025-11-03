package org.siri_hate.main_service.service.impl;

import org.siri_hate.main_service.model.dto.kafka.ProjectUpdateNotification;
import org.siri_hate.main_service.model.entity.Project;
import org.siri_hate.main_service.model.entity.ProjectSubscriber;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.repository.ProjectSubscriberRepository;
import org.siri_hate.main_service.service.KafkaService;
import org.siri_hate.main_service.service.ProjectService;
import org.siri_hate.main_service.service.ProjectSubscriberService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectSubscriberServiceImpl implements ProjectSubscriberService {

    private final ProjectSubscriberRepository projectSubscriberRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final KafkaService kafkaService;

    @Autowired
    public ProjectSubscriberServiceImpl(
            ProjectSubscriberRepository projectSubscriberRepository,
            @Lazy ProjectService projectService,
            @Lazy UserService userService,
            KafkaService kafkaService) {
        this.projectSubscriberRepository = projectSubscriberRepository;
        this.projectService = projectService;
        this.userService = userService;
        this.kafkaService = kafkaService;
    }

    @Override
    @Transactional
    public void subscribeToProject(Long projectId, String username) {
        User user = userService.findOrCreateUser(username);
        if (!projectSubscriberRepository.existsByProjectIdAndUserId(projectId, user.getId())) {
            Project project = projectService.getProjectById(projectId);
            ProjectSubscriber subscriber = new ProjectSubscriber(project, user);
            projectSubscriberRepository.save(subscriber);
        }
    }

    @Override
    @Transactional
    public void unsubscribeFromProject(Long projectId, String username) {
        User user = userService.findOrCreateUser(username);
        projectSubscriberRepository.deleteByProjectIdAndUserId(projectId, user.getId());
    }

    @Override
    @Transactional
    public void notifySubscribersAboutUpdate(Long projectId, String projectName) {
        List<ProjectSubscriber> subscribers = projectSubscriberRepository.findByProjectId(projectId);
        LocalDateTime updateDate = LocalDateTime.now();
        String projectLink = System.getenv("PROJECTS_LINK") + projectId;
        for (ProjectSubscriber subscriber : subscribers) {
            ProjectUpdateNotification notification =
                    new ProjectUpdateNotification(
                            projectName, updateDate.toString(), projectLink, subscriber.getUser().getUsername());
            kafkaService.sendProjectUpdateNotification(notification);
        }
    }

    @Override
    @Transactional
    public boolean isUserSubscribed(Long projectId, String username) {
        User user = userService.findOrCreateUser(username);
        return projectSubscriberRepository.existsByProjectIdAndUserId(projectId, user.getId());
    }
}
