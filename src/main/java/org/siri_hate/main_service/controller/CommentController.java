package org.siri_hate.main_service.controller;

import org.siri_hate.main_service.api.ProjectCommentApi;
import org.siri_hate.main_service.dto.CommentRequestDTO;
import org.siri_hate.main_service.dto.CommentResponseDTO;
import org.siri_hate.main_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentResponseDTO> createProjectComment(Long projectId, String xUserName, CommentRequestDTO commentRequestDTO) {
        var response = commentService.createComment(xUserName, projectId, commentRequestDTO);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long id, String xUserName) {
        commentService.deleteComment(id, xUserName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<CommentResponseDTO>> getProjectComments(Long projectId) {
        var response = commentService.getProjectComments(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}