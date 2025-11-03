package org.siri_hate.main_service.model.dto.request.project_members;

import jakarta.validation.constraints.NotBlank;

public class ProjectMemberRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Role should not be blank")
    private String role;

    public ProjectMemberRequest() {
    }

    public ProjectMemberRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
