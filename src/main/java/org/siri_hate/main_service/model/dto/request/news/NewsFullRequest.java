package org.siri_hate.main_service.model.dto.request.news;

import jakarta.validation.constraints.NotBlank;
import org.siri_hate.main_service.model.entity.category.NewsCategory;

public class NewsFullRequest {

    @NotBlank(message = "Title should not be blank")
    private String title;

    private String previewUrl;

    private NewsCategory category;

    @NotBlank(message = "Content should not be blank")
    private String content;

    public NewsFullRequest() {
    }

    public NewsFullRequest(String title, String previewUrl, NewsCategory category, String content) {
        this.title = title;
        this.previewUrl = previewUrl;
        this.category = category;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
