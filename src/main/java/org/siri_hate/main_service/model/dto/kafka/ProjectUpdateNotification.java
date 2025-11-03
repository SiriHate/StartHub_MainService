package org.siri_hate.main_service.model.dto.kafka;

public record ProjectUpdateNotification(String projectName, String updateDate, String projectLink, String username) {
}