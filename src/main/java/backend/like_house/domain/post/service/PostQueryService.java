package backend.like_house.domain.post.service;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.user.entity.User;

import java.util.List;

public interface PostQueryService {
    List<GetPostListResponse> getPostsByFamilySpace(Long familySpaceId, User user, Long cursor, int take);
    GetPostDetailResponse getPostDetail(Long postId, User user);
    List<GetMyPostListResponse> getMyPosts(User user);
}
