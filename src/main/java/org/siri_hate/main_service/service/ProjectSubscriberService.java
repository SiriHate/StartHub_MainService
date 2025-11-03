package org.siri_hate.main_service.service;

public interface ProjectSubscriberService {
    void subscribeToProject(Long projectId, String username);

    void unsubscribeFromProject(Long projectId, String username);

    void notifySubscribersAboutUpdate(Long projectId, String projectName);

    boolean isUserSubscribed(Long projectId, String username);
}
