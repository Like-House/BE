package backend.like_house.domain.post.converter;

import backend.like_house.domain.account.entity.Custom;
import backend.like_house.domain.post.dto.PostDTO.*;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.post.entity.PostImage;
import backend.like_house.domain.post.entity.UserPostTag;
import backend.like_house.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponse.GetPostListResponse toGetPostListResponse(Post post, String authorNickname, int likeCount, int commentCount, List<String> imageUrls) {
        return PostResponse.GetPostListResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .authorNickname(authorNickname)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .imageUrls(imageUrls)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static List<PostResponse.GetPostListResponse> toGetPostListResponse(List<Post> posts, List<String> authorNicknames, List<Integer> likeCounts, List<Integer> commentCounts, List<List<String>> imageUrlsList) {
        return posts.stream()
                .map(post -> toGetPostListResponse(
                        post,
                        authorNicknames.get(posts.indexOf(post)),
                        likeCounts.get(posts.indexOf(post)),
                        commentCounts.get(posts.indexOf(post)),
                        imageUrlsList.get(posts.indexOf(post))
                ))
                .collect(Collectors.toList());
    }

    public static PostResponse.GetPostDetailResponse toGetPostDetailResponse(Post post, String authorNickname, int likeCount, int commentCount, List<String> imageUrls, List<PostResponse.FamilyTagResponse> taggedUsers) {
        return PostResponse.GetPostDetailResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .authorNickname(authorNickname)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .imageUrls(imageUrls)
                .taggedUsers(taggedUsers)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static List<PostResponse.FamilyTagResponse> toGetFamilyTagResponse(List<User> users, List<Custom> customs) {
        return users.stream()
                .map(user -> PostResponse.FamilyTagResponse.builder()
                        .userId(user.getId())
                        .nickname(getNicknameForUser(user, customs))
                        .build())
                .collect(Collectors.toList());
    }

    private static String getNicknameForUser(User user, List<Custom> customs) {
        return customs.stream()
                .filter(custom -> custom.getContact().getProfileId().equals(user.getId()))
                .findFirst()
                .map(Custom::getNickname)
                .orElse(user.getName());
    }

    public static PostResponse.CreatePostResponse toCreatePostResponse(Post post) {
        return PostResponse.CreatePostResponse.builder()
                .postId(post.getId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
