package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;

public interface CommentCommandService {
    CreateCommentResponse createComment(Long userId, CreateCommentRequest request);
    CreateCommentResponse updateComment(Long userId, Long commentId, UpdateCommentRequest request);
    void deleteComment(Long userId, Long commentId);
}
