package backend.like_house.domain.post.service.impl;

import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.converter.CommentConverter;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.entity.Comment;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.service.PostQueryService;
import backend.like_house.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryServiceImpl implements PostQueryService {

    @Override
    public List<GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, User user, Long cursor, int take) {
        List<Post> posts = null;

        List<GetPostListResponse> postListResponses = posts.stream().map(post -> {
            String authorNickname = null;
            String profileImage = null;
            int likeCount = 0;
            int commentCount = 0;
            List<String> imageUrls = null;
            return PostConverter.toGetPostListResponse(post, authorNickname, profileImage, likeCount, commentCount, imageUrls);
        }).collect(Collectors.toList());

        // TODO: 가족 공간 ID로 게시글을 가져오는 로직
        // (글 쓴 사람) post에 있는 userId == profileId이고 (사용자) userId == contact의 user_id인 nickname
        return postListResponses;
    }

    @Override
    public GetPostDetailResponse getPostDetail(Long postId, User user) {
        Post post = null;
        String authorNickname = null;
        String profileImage = null;
        int likeCount = 0;
        int commentCount = 0;
        List<String> imageUrls = null;
        List<FamilyTagResponse> taggedUsers = null;

        List<Comment> comments = null;
        List<GetCommentResponse> commentResponses = comments.stream()
                .map(comment -> {
                    // User user = userRepository.findById(comment.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
                    // String userNickname = user.getNickname();
                    String userNickname = null;
                    return CommentConverter.toGetCommentResponse(comment, userNickname);
                })
                .collect(Collectors.toList());

        // TODO: postId를 사용하여 특정 게시글의 상세 정보를 조회하는 로직
        // 1. postId를 기반으로 게시글 조회
        // 2. 조회한 게시글의 상세 정보 반환
        // 3. + 댓글 정보
        return PostConverter.toGetPostDetailResponse(post, authorNickname, profileImage, likeCount, commentCount, imageUrls, taggedUsers, commentResponses);
    }

    @Override
    public List<GetMyPostListResponse> getMyPosts(User user) {
        List<Post> posts = null;

        // TODO: userId를 기반으로 사용자가 작성한 게시글을 조회하는 로직
        return posts.stream()
                .map(post -> {
                    List<FamilyTagResponse> taggedUsers = null; // 1. 태그된 사용자 리스트 조회
                    List<String> imageUrls = null; // 2. 이미지 URL 리스트 조회
                    return PostConverter.toGetMyPostListResponse(post, taggedUsers, imageUrls);
                })
                .collect(Collectors.toList());
    }
}