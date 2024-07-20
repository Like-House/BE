package backend.like_house.domain.post.converter;

import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.user.entity.User;

public class CommentConverter {

    public static CommentDTO.CommentResponse.CreateCommentResponse toCreateCommentResponse(Comment comment) {
        return CommentDTO.CommentResponse.CreateCommentResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static CommentDTO.CommentResponse.GetCommentResponse toGetCommentResponse(Comment comment, String userNickname) {
        return CommentDTO.CommentResponse.GetCommentResponse.builder()
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
