package org.siri_hate.main_service.model.dto.request.category;

import jakarta.validation.constraints.NotBlank;

public class NewsCategoryRequest {

    @NotBlank(message = "Name is required")
    private String name;

    public NewsCategoryRequest() {
    }

    public NewsCategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
