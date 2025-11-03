package org.siri_hate.main_service.controller;

import org.siri_hate.main_service.model.dto.request.comment.CommentRequest;
import org.siri_hate.main_service.model.dto.response.comment.CommentResponse;
import org.siri_hate.main_service.model.entity.Comment;
import org.siri_hate.main_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main_service")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/projects/{projectId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long projectId, @RequestBody CommentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Comment comment = commentService.createComment(username, projectId, request);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/projects/{projectId}/comments")
    public ResponseEntity<List<CommentResponse>> getProjectComments(@PathVariable Long projectId) {
        List<CommentResponse> comments = commentService.getProjectComments(projectId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.ok().build();
    }
}
