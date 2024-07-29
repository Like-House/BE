package backend.like_house.domain.post.converter;

import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.entity.Post;

import java.time.LocalDate;
import java.util.List;

public class PostConverter {

    public static GetPostListResponse toGetPostListResponse(Post post, String authorNickname, String profileImage, int likeCount, int commentCount, List<String> imageUrls, boolean owner, List<LocalDate> scheduledDates) {
        return GetPostListResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .authorNickname(authorNickname)
                .profileImage(profileImage)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .imageUrls(imageUrls)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .owner(owner)
                .scheduledDates(scheduledDates)
                .build();
    }

    public static GetPostDetailResponse toGetPostDetailResponse(Post post, String authorNickname, String profileImage, int likeCount, int commentCount, List<String> imageUrls, List<FamilyTagResponse> taggedUsers, List<CommentDTO.CommentResponse.GetCommentResponse> comments) {
        return GetPostDetailResponse.builder()
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

    public static CreatePostResponse toCreatePostResponse(Post post) {
        return CreatePostResponse.builder()
                .postId(post.getId())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static GetMyPostListResponse toGetMyPostListResponse(Post post, List<FamilyTagResponse> taggedUsers, List<String> imageUrls) {
        return GetMyPostListResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .taggedUsers(taggedUsers)
                .createdAt(post.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
    }
}
