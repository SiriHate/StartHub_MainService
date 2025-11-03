package org.siri_hate.main_service.model.dto.response.project;

public class ProjectSummaryResponse {

    private Long id;
    private String projectOwner;
    private String projectName;
    private String projectLogoUrl;
    private String category;
    private Long likes;

    public ProjectSummaryResponse() {
    }

    public ProjectSummaryResponse(
            Long id,
            String projectOwner,
            String projectName,
            String projectLogoUrl,
            String category,
            Long likes) {
        this.id = id;
        this.projectOwner = projectOwner;
        this.projectName = projectName;
        this.projectLogoUrl = projectLogoUrl;
        this.category = category;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLogoUrl() {
        return projectLogoUrl;
    }

    public void setProjectLogoUrl(String projectLogoUrl) {
        this.projectLogoUrl = projectLogoUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}
