package backend.like_house.domain.post.converter;

import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.user.entity.User;

public class CommentConverter {

    public static Comment toComment(CreateCommentRequest request, Post post, User user, Comment parent) {
        return Comment.builder()
                .post(post)
                .user(user)
                .parent(parent)
                .content(request.getContent())
                .title(request.getTitle())
                .build();
    }

    public static CreateCommentResponse toCreateCommentResponse(Comment comment) {
        return CreateCommentResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .title(comment.getTitle())
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
                .title(comment.getTitle())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
