package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.ProjectFullResponseDTO;
import org.siri_hate.main_service.dto.ProjectPageResponseDTO;
import org.siri_hate.main_service.dto.ProjectRequestDTO;
import org.siri_hate.main_service.dto.ProjectSummaryResponseDTO;
import org.siri_hate.main_service.model.entity.project.Project;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class,
                ProjectMemberMapper.class,
                CommentMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {

    @Mapping(target = "category", ignore = true)
    Project toProject(ProjectRequestDTO project);

    void projectUpdate(ProjectRequestDTO request, @MappingTarget Project project);

    @Mapping(target = "category", ignore = true)
    ProjectFullResponseDTO toProjectFullResponse(Project project);

    @Mapping(target = "category", ignore = true)
    ProjectSummaryResponseDTO toProjectSummaryResponse(Project project);

    ProjectPageResponseDTO toProjectPageResponse(Page<Project> projects);
}