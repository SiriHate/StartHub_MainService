package org.siri_hate.main_service.model.dto.response.category;

public class ProjectCategoryFullResponse {

    private Long id;
    private String name;

    public ProjectCategoryFullResponse() {
    }

    public ProjectCategoryFullResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
