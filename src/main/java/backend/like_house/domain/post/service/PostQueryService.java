package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.PostDTO;

import java.util.List;

public interface PostQueryService {
    List<PostDTO.PostResponse.GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, Long userId, Long cursor, int take);
    PostDTO.PostResponse.GetPostDetailResponse getPostDetail(Long postId, Long userId);
    List<PostDTO.PostResponse.FamilyTagResponse> getFamilyTags(Long familySpaceId, Long userId);
    List<PostDTO.PostResponse.GetMyPostListResponse> getMyPosts(Long userId);
}
