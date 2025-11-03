package org.siri_hate.main_service.service.impl;

import com.google.gson.Gson;
import org.siri_hate.main_service.model.dto.kafka.UserDeletionMessage;
import org.siri_hate.main_service.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final UserService userService;
    private final Gson gson = new Gson();

    public KafkaConsumerService(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(
            topics = "${user.deletion.topic.s2m.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserDeletionMessage(String message) {
        UserDeletionMessage userDeletionMessage = gson.fromJson(message, UserDeletionMessage.class);
        try {
            userService.deleteUserByUsername(userDeletionMessage.username());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
