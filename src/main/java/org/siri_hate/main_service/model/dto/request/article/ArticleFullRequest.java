package org.siri_hate.main_service.model.dto.request.article;

import jakarta.validation.constraints.NotBlank;
import org.siri_hate.main_service.model.entity.category.ArticleCategory;

public class ArticleFullRequest {

    @NotBlank
    String title;

    String previewUrl;

    ArticleCategory category;

    @NotBlank
    String content;

    public ArticleFullRequest() {
    }

    public ArticleFullRequest(
            String title, String previewUrl, ArticleCategory category, String content) {
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

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
