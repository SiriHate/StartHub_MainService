package org.siri_hate.main_service.model.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.siri_hate.main_service.model.dto.request.project_members.ProjectMemberRequest;
import org.siri_hate.main_service.model.entity.category.ProjectCategory;

import java.util.HashSet;
import java.util.Set;

public class ProjectFullRequest {

    private Set<ProjectMemberRequest> members = new HashSet<>();

    @NotBlank(message = "Project name should not be blank")
    private String projectName;

    @NotBlank(message = "Project description should not be blank")
    private String projectDescription;

    private String projectLogoUrl;

    @NotNull(message = "Project category must be specified")
    private ProjectCategory category;

    public ProjectFullRequest() {
    }

    public ProjectFullRequest(
            Set<ProjectMemberRequest> members,
            String projectName,
            String projectDescription,
            String projectLogoUrl,
            ProjectCategory category) {
        this.members = members;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectLogoUrl = projectLogoUrl;
        this.category = category;
    }

    public Set<ProjectMemberRequest> getMembers() {
        return members;
    }

    public void setMembers(Set<ProjectMemberRequest> members) {
        this.members = members;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectLogoUrl() {
        return projectLogoUrl;
    }

    public void setProjectLogoUrl(String projectLogoUrl) {
        this.projectLogoUrl = projectLogoUrl;
    }

    public ProjectCategory getCategory() {
        return category;
    }

    public void setCategory(ProjectCategory category) {
        this.category = category;
    }
}
