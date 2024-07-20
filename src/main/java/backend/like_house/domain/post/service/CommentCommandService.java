package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.CommentDTO;

public interface CommentCommandService {
    CommentDTO.CommentResponse.CreateCommentResponse createComment(Long userId, CommentDTO.CommentRequest.CreateCommentRequest request);
    CommentDTO.CommentResponse.CreateCommentResponse updateComment(Long userId, Long commentId, CommentDTO.CommentRequest.UpdateCommentRequest request);
    void deleteComment(Long userId, Long commentId);
}
