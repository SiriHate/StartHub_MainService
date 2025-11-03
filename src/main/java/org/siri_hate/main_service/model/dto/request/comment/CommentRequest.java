package org.siri_hate.main_service.model.dto.request.comment;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank(message = "Текст комментария не может быть пустым")
    private String text;

    public CommentRequest() {
    }

    public CommentRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
