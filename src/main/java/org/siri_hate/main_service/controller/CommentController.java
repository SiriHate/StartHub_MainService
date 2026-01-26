package org.siri_hate.main_service.controller;

import org.siri_hate.main_service.api.ProjectCommentApi;
import org.siri_hate.main_service.dto.CommentFullResponseDTO;
import org.siri_hate.main_service.dto.CommentRequestDTO;
import org.siri_hate.main_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController implements ProjectCommentApi {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<CommentFullResponseDTO> createProjectComment(Long projectId, CommentRequestDTO commentRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var response = commentService.createComment(username, projectId, commentRequestDTO);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        commentService.deleteComment(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<CommentFullResponseDTO>> getProjectComments(Long projectId) {
        var response = commentService.getProjectComments(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}