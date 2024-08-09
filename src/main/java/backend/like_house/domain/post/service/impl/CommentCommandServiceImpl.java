package backend.like_house.domain.post.service.impl;
import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.notification.service.NotificationCommandService;
import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.post.converter.CommentConverter;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.repository.CommentRepository;
import backend.like_house.domain.post.repository.PostRepository;
import backend.like_house.domain.post.service.CommentCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.common.enums.NotificationType;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.CommentException;
import backend.like_house.global.error.handler.PostException;
import backend.like_house.global.firebase.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NotificationCommandService notificationCommandService;
    private final FcmService fcmService;
    private final UserRepository userRepository;

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

        Comment comment = CommentConverter.toComment(request, post, user, parent);
        comment = commentRepository.save(comment);

        // 게시 물에 댓글이 달렸을 때
        if (parent == null && post.getPostAlarm()) {
            notificationCommandService.saveNotification(user, post.getUser(),user.getName()+"가 게시 물에 댓글을 달았 습니다.", request.getContent(), NotificationType.POST);
            fcmService.sendNotification(post.getUser().getFcmToken(), user.getName()+"가 게시 물에 댓글을 달았 습니다.", request.getContent());
        }

        // 댓글에 대 댓글이 달렸을 때
        if (parent != null && parent.getCommentAlarm()) {
            notificationCommandService.saveNotification(user, parent.getUser(),user.getName()+"가 댓글에 대 댓글을 달았 습니다.", request.getContent(), NotificationType.POST);
            fcmService.sendNotification(parent.getUser().getFcmToken(), user.getName()+"가 댓글에 대 댓글을 달았 습니다.", request.getContent());
        }

        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Transactional
    @Override
    public CreateCommentResponse updateComment(User user, Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentException(ErrorStatus.COMMENT_INVALID_ACCESS);
        }

        comment.setContent(request.getContent());

        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Transactional
    @Override
    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentException(ErrorStatus.COMMENT_INVALID_ACCESS);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public void toggleCommentAlarm(User user, Long commentId, Boolean enable) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentException(ErrorStatus.COMMENT_INVALID_ACCESS);
        }

        comment.setCommentAlarm(enable);
    }
}
