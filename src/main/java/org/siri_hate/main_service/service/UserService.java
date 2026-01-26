package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.entity.User;

public interface UserService {
    User findOrCreateUser(String username);

    void deleteUser(String username);
}