package backend.like_house.domain.post.converter;

import backend.like_house.domain.account.entity.Custom;
import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.dto.PostDTO.*;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import backend.like_house.domain.post.entity.UserPostTag;
import backend.like_house.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponse.GetPostListResponse toGetPostListResponse(Post post, String authorNickname, String profileImage, int likeCount, int commentCount, List<String> imageUrls) {
        return PostResponse.GetPostListResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .authorNickname(authorNickname)
                .profileImage(profileImage)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .imageUrls(imageUrls)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static PostResponse.GetPostDetailResponse toGetPostDetailResponse(Post post, String authorNickname, String profileImage, int likeCount, int commentCount, List<String> imageUrls, List<PostResponse.FamilyTagResponse> taggedUsers, List<CommentDTO.CommentResponse.GetCommentResponse> comments) {
        return PostResponse.GetPostDetailResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .authorNickname(authorNickname)
                .profileImage(profileImage)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .imageUrls(imageUrls)
                .taggedUsers(taggedUsers)
                .comments(comments)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static PostResponse.FamilyTagResponse toGetFamilyTagResponse(Long userId, String nickname) {
        return PostResponse.FamilyTagResponse.builder()
                .userId(userId)
                .nickname(nickname)
                .build();
    }

    public static PostResponse.CreatePostResponse toCreatePostResponse(Post post) {
        return PostResponse.CreatePostResponse.builder()
                .postId(post.getId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
