package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.user.entity.User;

public interface CommentCommandService {
    CreateCommentResponse createComment(User user, CreateCommentRequest request);
    CreateCommentResponse updateComment(User user, Long commentId, UpdateCommentRequest request);
    void deleteComment(User user, Long commentId);
    void toggleCommentAlarm(User user, Long commentId, Boolean enable);
}
