package backend.like_house.domain.post.service.impl;

import backend.like_house.domain.account.entity.Custom;
import backend.like_house.domain.post.converter.CommentConverter;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.dto.PostDTO;
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
    public List<PostDTO.PostResponse.GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, Long userId, Long cursor, int take) {
        List<Post> posts = null;

        List<PostDTO.PostResponse.GetPostListResponse> postListResponses = posts.stream().map(post -> {
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
    public PostDTO.PostResponse.GetPostDetailResponse getPostDetail(Long postId, Long userId) {
        Post post = null;
        String authorNickname = null;
        String profileImage = null;
        int likeCount = 0;
        int commentCount = 0;
        List<String> imageUrls = null;
        List<PostDTO.PostResponse.FamilyTagResponse> taggedUsers = null;

        List<Comment> comments = null;
        List<CommentDTO.CommentResponse.GetCommentResponse> commentResponses = comments.stream()
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
    public List<PostDTO.PostResponse.FamilyTagResponse> getFamilyTags(Long familySpaceId, Long userId) {
        List<User> users = null; // 가족 공간 멤버 조회
        List<Custom> customs = null; // 특정 사용자가 해당 가족 공간의 멤버들에게 설정한 별명

        // TODO: familySpaceId를 기반으로 가족 태그를 조회하는 로직
        // 1. familySpaceId와 userId를 기반으로 해당 가족 공간의 멤버들 조회
        // 2. 각 멤버에 대해 사용자가 설정한 별명으로 반환

        return users.stream()
                .map(user -> {
                    String nickname = customs.stream()
                            .filter(custom -> custom.getContact().getProfileId().equals(user.getId()))
                            .findFirst()
                            .map(Custom::getNickname)
                            .orElse(user.getName());
                    return PostConverter.toGetFamilyTagResponse(user.getId(), nickname);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO.PostResponse.GetMyPostListResponse> getMyPosts(Long userId) {
        List<Post> posts = null;

        // TODO: userId를 기반으로 사용자가 작성한 게시글을 조회하는 로직
        return posts.stream()
                .map(post -> {
                    List<PostDTO.PostResponse.FamilyTagResponse> taggedUsers = null; // 1. 태그된 사용자 리스트 조회
                    List<String> imageUrls = null; // 2. 이미지 URL 리스트 조회
                    return PostConverter.toGetMyPostListResponse(post, taggedUsers, imageUrls);
                })
                .collect(Collectors.toList());
    }
}