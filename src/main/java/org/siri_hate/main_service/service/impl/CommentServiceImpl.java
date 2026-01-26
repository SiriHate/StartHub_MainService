package org.siri_hate.main_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.siri_hate.main_service.dto.CommentFullResponseDTO;
import org.siri_hate.main_service.dto.CommentRequestDTO;
import org.siri_hate.main_service.model.mapper.CommentMapper;
import org.siri_hate.main_service.model.entity.Comment;
import org.siri_hate.main_service.model.entity.project.Project;
import org.siri_hate.main_service.model.entity.User;
import org.siri_hate.main_service.repository.CommentRepository;
import org.siri_hate.main_service.service.CommentService;
import org.siri_hate.main_service.service.ProjectService;
import org.siri_hate.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            CommentMapper commentMapper
    ) {
        this.commentRepository = commentRepository;
        this.projectService = projectService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public CommentFullResponseDTO createComment(String username, Long projectId, CommentRequestDTO request) {
        Project project = projectService.getProjectEntity(projectId);
        User user = userService.findOrCreateUser(username);
        var comment = new Comment(user, project, request.getText());
        commentRepository.save(comment);
        return commentMapper.toCommentFullResponseDTO(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        String commentOwnerUsername = comment.getAuthor().getUsername();
        if (!commentOwnerUsername.equals(username)) {
            throw new RuntimeException();
        }
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public List<CommentFullResponseDTO> getProjectComments(Long projectId) {
        List<Comment> comments = commentRepository.findByProjectId(projectId);
        return commentMapper.toCommentFullResponseListDTO(comments);
    }
}
