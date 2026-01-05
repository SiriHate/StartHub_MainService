package org.siri_hate.main_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.siri_hate.main_service.model.dto.request.project.ProjectFullRequest;
import org.siri_hate.main_service.model.dto.response.comment.CommentResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectFullResponse;
import org.siri_hate.main_service.model.dto.response.project.ProjectSummaryResponse;
import org.siri_hate.main_service.model.entity.Comment;
import org.siri_hate.main_service.model.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ProjectMemberMapper.class, CommentMapper.class})
public interface ProjectMapper {

    Project toProject(ProjectFullRequest project);

    void projectUpdate(ProjectFullRequest request, @MappingTarget Project existingProject);

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "owner", target = "projectOwner")
    @Mapping(source = "members", target = "members")
    @Mapping(target = "likes", expression = "java(getLikesCount(project))")
    @Mapping(target = "hasSurvey", expression = "java(project.getSurvey() != null)")
    @Mapping(target = "comments", expression = "java(mapComments(project.getComments()))")
    ProjectFullResponse toProjectFullResponse(Project project);

    @Mapping(source = "category.name", target = "category")
    @Mapping(target = "likes", expression = "java(getLikesCount(project))")
    ProjectSummaryResponse toProjectSummaryResponse(Project project);

    List<ProjectSummaryResponse> toProjectSummaryResponseList(List<Project> projects);

    default Page<ProjectSummaryResponse> toProjectSummaryResponsePage(Page<Project> projects) {
        List<ProjectSummaryResponse> summaryResponses = toProjectSummaryResponseList(projects.getContent());
        return new PageImpl<>(summaryResponses, projects.getPageable(), projects.getTotalElements());
    }

    default Long getLikesCount(Project project) {
        return (long) project.getProjectLikes().size();
    }

    default List<CommentResponse> mapComments(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream().map(CommentMapper.INSTANCE::toCommentResponse).collect(Collectors.toList());
    }
}
