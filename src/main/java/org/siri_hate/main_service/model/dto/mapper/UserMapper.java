package org.siri_hate.main_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.siri_hate.main_service.model.dto.response.user.UserFullResponse;
import org.siri_hate.main_service.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserFullResponse toUserFullResponse(User user);
}