package org.siri_hate.main_service.service.impl;

import org.siri_hate.main_service.model.dto.kafka.ProjectUpdateNotification;
import org.siri_hate.main_service.model.dto.kafka.UserDeletionMessage;
import org.siri_hate.main_service.service.KafkaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, UserDeletionMessage> userDeletionKafkaTemplate;
    private final KafkaTemplate<String, ProjectUpdateNotification> projectUpdateKafkaTemplate;

    @Value("${user.deletion.topic.m2s.name}")
    private String userDeletionTopic;

    @Value("${project.update.notification.topic}")
    private String projectUpdateTopic;

    public KafkaServiceImpl(
            KafkaTemplate<String, UserDeletionMessage> userDeletionKafkaTemplate,
            KafkaTemplate<String, ProjectUpdateNotification> projectUpdateKafkaTemplate) {
        this.userDeletionKafkaTemplate = userDeletionKafkaTemplate;
        this.projectUpdateKafkaTemplate = projectUpdateKafkaTemplate;
    }

    @Override
    public void sendUserDeletionMessage(String username) {
        UserDeletionMessage message = new UserDeletionMessage(username);
        userDeletionKafkaTemplate.send(userDeletionTopic, message);
    }

    @Override
    public void sendProjectUpdateNotification(ProjectUpdateNotification notification) {
        projectUpdateKafkaTemplate.send(projectUpdateTopic, notification);
    }
}
