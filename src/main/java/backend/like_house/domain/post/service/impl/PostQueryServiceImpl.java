package backend.like_house.domain.post.service.impl;

import backend.like_house.domain.account.entity.Custom;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.dto.PostDTO;
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
public class PostQueryServiceImpl implements PostQueryService {

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO.PostResponse.GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, Long userId, Long cursor, int take) {
        List<Post> posts = null;

        List<PostDTO.PostResponse.GetPostListResponse> postListResponses = posts.stream().map(post -> {
            String authorNickname = null;
            int likeCount = 0;
            int commentCount = 0;
            List<String> imageUrls = null;
            return PostConverter.toGetPostListResponse(post, authorNickname, likeCount, commentCount, imageUrls);
        }).collect(Collectors.toList());

        // TODO: 가족 공간 ID로 게시글을 가져오는 로직
        // (글 쓴 사람) post에 있는 userId == profileId이고 (사용자) userId == contact의 user_id인 nickname
        return postListResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public PostDTO.PostResponse.GetPostDetailResponse getPostDetail(Long postId, Long userId) {
        Post post = null;
        String authorNickname = null;
        int likeCount = 0;
        int commentCount = 0;
        List<String> imageUrls = null;
        List<PostDTO.PostResponse.FamilyTagResponse> taggedUsers = null;

        // TODO: postId를 사용하여 특정 게시글의 상세 정보를 조회하는 로직
        // 1. postId를 기반으로 게시글 조회
        // 2. 조회한 게시글의 상세 정보 반환
        return PostConverter.toGetPostDetailResponse(post, authorNickname, likeCount, commentCount, imageUrls, taggedUsers);
    }

    @Transactional(readOnly = true)
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
}
