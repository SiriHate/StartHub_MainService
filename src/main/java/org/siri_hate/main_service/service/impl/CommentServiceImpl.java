package org.siri_hate.main_service.service.impl;

import org.siri_hate.main_service.model.dto.mapper.CommentMapper;
import org.siri_hate.main_service.model.dto.request.comment.CommentRequest;
import org.siri_hate.main_service.model.dto.response.comment.CommentResponse;
import org.siri_hate.main_service.model.entity.Comment;
import org.siri_hate.main_service.model.entity.Project;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.repository.CommentRepository;
import org.siri_hate.main_service.service.CommentService;
import org.siri_hate.main_service.service.ProjectService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            ProjectService projectService,
            UserService userService,
            CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.projectService = projectService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public Comment createComment(String username, Long projectId, CommentRequest request) {
        Project project = projectService.getProjectById(projectId);
        User user = userService.findOrCreateUser(username);
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setProject(project);
        comment.setUser(user);
        comment.setCreatedDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("User is not authorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getProjectComments(Long projectId) {
        List<Comment> comments = commentRepository.findByProjectId(projectId);
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }
}
