package backend.like_house.domain.post.converter;

import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.dto.PostDTO.*;
import backend.like_house.domain.post.entity.Post;

import java.util.List;

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

    public static PostResponse.GetMyPostListResponse toGetMyPostListResponse(Post post, List<PostResponse.FamilyTagResponse> taggedUsers, List<String> imageUrls) {
        return PostResponse.GetMyPostListResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .taggedUsers(taggedUsers)
                .createdAt(post.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
    }
}
