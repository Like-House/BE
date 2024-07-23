package backend.like_house.domain.post.service.impl;
import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.post.converter.CommentConverter;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.service.CommentCommandService;
import backend.like_house.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    @Transactional
    @Override
    public CreateCommentResponse createComment(Long userId, CreateCommentRequest request) {
        User user = null;
        Post post = null;
        // Comment parent = request.getParentId() != null ? commentRepository.findById(request.getParentId()).orElseThrow() : null;
        Comment parent = null;
        Comment comment = null;

        // TODO: 새로운 댓글을 작성하는 로직
        // 1. userId를 사용하여 userRepository에서 댓글을 작성하는 사용자 조회
        // 2. postId를 사용하여 postRepository에서 댓글이 추가될 게시글 조회
        // 3. 요청에 parentId가 있는 경우, parentId를 사용하여 commentRepository에서 상위 댓글 조회(답글인 경우)
        // 4. 요청 데이터와 조회된 엔티티로 새로운 댓글 생성
        // 5. 응답 DTO로 변환하고 반환
        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Transactional
    @Override
    public CreateCommentResponse updateComment(Long userId, Long commentId, UpdateCommentRequest request) {
        Comment comment = null;

        // TODO: 특정 댓글을 수정하는 로직
        // 1. commentId를 사용하여 commentRepository에서 업데이트할 댓글 조회
        // 2. 요청 데이터에서 가져온 내용으로 댓글 내용 업데이트
        // 3. 업데이트된 댓글 저장
        // 4. 응답 DTO로 변환하고 반환
        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long userId, Long commentId) {
        // TODO: 특정 댓글을 삭제하는 로직
        // 1. commentId를 사용하여 commentRepository에서 삭제할 댓글 조회
        // 2. 댓글 삭제
    }
}
