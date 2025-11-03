package org.siri_hate.main_service.service;

import org.siri_hate.main_service.model.dto.request.comment.CommentRequest;
import org.siri_hate.main_service.model.dto.response.comment.CommentResponse;
import org.siri_hate.main_service.model.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(String username, Long projectId, CommentRequest request);

    void deleteComment(Long commentId, String username);

    List<CommentResponse> getProjectComments(Long projectId);
}
