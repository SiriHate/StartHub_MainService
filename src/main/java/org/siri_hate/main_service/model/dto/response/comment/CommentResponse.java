package org.siri_hate.main_service.model.dto.response.comment;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private Long userId;
    private String username;
    private String text;
    private LocalDateTime createdDate;

    public CommentResponse() {
    }

    public CommentResponse(
            Long id, Long userId, String username, String text, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.text = text;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
