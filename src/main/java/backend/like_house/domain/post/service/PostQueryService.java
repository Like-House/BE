package backend.like_house.domain.post.service;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;

import java.util.List;

public interface PostQueryService {
    List<GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, Long userId, Long cursor, int take);
    GetPostDetailResponse getPostDetail(Long postId, Long userId);
    List<FamilyTagResponse> getFamilyTags(Long familySpaceId, Long userId);
    List<GetMyPostListResponse> getMyPosts(Long userId);
}
