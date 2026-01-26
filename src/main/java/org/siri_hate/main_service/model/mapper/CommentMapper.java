package org.siri_hate.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.siri_hate.main_service.dto.CommentFullResponseDTO;
import org.siri_hate.main_service.dto.CommentRequestDTO;
import org.siri_hate.main_service.model.entity.Comment;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {
    Comment commentDtoToComment(CommentRequestDTO commentRequest);

    CommentFullResponseDTO toCommentFullResponseDTO(Comment comment);

    List<CommentFullResponseDTO> toCommentFullResponseListDTO(List<Comment> comments);
}