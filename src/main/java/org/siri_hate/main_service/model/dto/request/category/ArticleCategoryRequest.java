package org.siri_hate.main_service.model.dto.request.category;

import jakarta.validation.constraints.NotBlank;

public class ArticleCategoryRequest {

    @NotBlank(message = "Name is required")
    private String name;

    public ArticleCategoryRequest() {
    }

    public ArticleCategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
