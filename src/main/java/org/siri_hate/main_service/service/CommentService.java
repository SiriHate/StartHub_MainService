package org.siri_hate.main_service.service;

import org.siri_hate.main_service.dto.CommentFullResponseDTO;
import org.siri_hate.main_service.dto.CommentRequestDTO;

import java.util.List;

public interface CommentService {
    CommentFullResponseDTO createComment(String username, Long projectId, CommentRequestDTO request);

    void deleteComment(Long commentId, String username);

    List<CommentFullResponseDTO> getProjectComments(Long projectId);
}