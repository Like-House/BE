package backend.like_house.domain.post.service.impl;
import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.post.converter.CommentConverter;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.repository.CommentRepository;
import backend.like_house.domain.post.repository.PostRepository;
import backend.like_house.domain.post.service.CommentCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.CommentException;
import backend.like_house.global.error.handler.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public CreateCommentResponse createComment(User user, CreateCommentRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));
        }

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .parent(parent)
                .content(request.getContent())
                .build();

        comment = commentRepository.save(comment);

        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Transactional
    @Override
    public CreateCommentResponse updateComment(User user, Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentException(ErrorStatus.INVALID_ACCESS);
        }

        comment.setContent(request.getContent());

        comment = commentRepository.save(comment);

        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Transactional
    @Override
    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentException(ErrorStatus.INVALID_ACCESS);
        }

        commentRepository.delete(comment);
    }
}
