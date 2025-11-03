package org.siri_hate.main_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siri_hate.main_service.model.dto.response.project_members.ProjectMembersFullResponse;
import org.siri_hate.main_service.model.dto.response.project_members.ProjectMembersSummaryResponse;
import org.siri_hate.main_service.model.entity.ProjectMember;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ProjectMemberMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "role", target = "role")
    ProjectMembersFullResponse toProjectMembersFullResponse(ProjectMember projectMember);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "role", target = "role")
    ProjectMembersSummaryResponse toProjectMembersSummaryResponse(ProjectMember projectMember);
}
