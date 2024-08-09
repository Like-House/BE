package backend.like_house.domain.post.converter;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import backend.like_house.domain.post.entity.PostLike;
import backend.like_house.domain.post.entity.UserPostTag;
import backend.like_house.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(CreatePostRequest createPostRequest, FamilySpace familySpace, User user) {
        return Post.builder()
                .familySpace(familySpace)
                .user(user)
                .content(createPostRequest.getContent())
                .build();
    }

    public static List<PostImage> toPostImages(List<String> imageUrls, Post post) {
        return imageUrls.stream()
                .map(url -> PostImage.builder().post(post).filename(url).build())
                .collect(Collectors.toList());
    }

    public static List<UserPostTag> toUserPostTags(List<FamilyTagResponse> taggedUsers, Post post) {
        return taggedUsers.stream()
                .map(taggedUser -> UserPostTag.builder()
                        .user(User.builder().id(taggedUser.getUserId()).build())
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }

    public static PostLike toPostLike(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }

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

    public static MyPostCursorDataListResponse toMyPostCursorDataListResponse(List<GetMyPostListResponse> postResponses, Long nextCursor) {
        return MyPostCursorDataListResponse.builder()
                .posts(postResponses)
                .nextCursor(nextCursor)
                .build();
    }
}
