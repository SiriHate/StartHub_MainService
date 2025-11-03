package org.siri_hate.main_service.model.dto.response.article;

public class ArticleSummaryResponse {

    private Long id;
    private String title;
    private String owner;
    private String previewUrl;
    private String category;

    public ArticleSummaryResponse() {
    }

    public ArticleSummaryResponse(
            Long id, String title, String owner, String previewUrl, String category) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.previewUrl = previewUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
