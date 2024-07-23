package backend.like_house.domain.post.converter;

import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.post.entity.Comment;

public class CommentConverter {

    public static CreateCommentResponse toCreateCommentResponse(Comment comment) {
        return CreateCommentResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static GetCommentResponse toGetCommentResponse(Comment comment, String userNickname) {
        return GetCommentResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .userId(comment.getUser().getId())
                .userNickname(userNickname)
                .userProfileImage(comment.getUser().getProfileImage())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
