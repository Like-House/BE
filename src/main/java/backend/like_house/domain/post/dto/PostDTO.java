package backend.like_house.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDTO {

    public static class PostRequest {

        @Getter
        public static class CreatePostRequest {
            @NotNull
            private Long familySpaceId;
            private String content;
            private List<PostResponse.FamilyTagResponse> taggedUserIds;
        }

        @Getter
        public static class UpdatePostRequest {
            @NotNull
            private Long postId;
            private String content;
            private List<PostResponse.FamilyTagResponse> taggedUserIds;
        }
    }

    public static class PostResponse {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class GetPostListResponse {
            private Long postId;
            private String content;
            private String authorNickname;
            private String profileImage;
            private int likeCount;
            private int commentCount;
            private List<String> imageUrls;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class GetPostDetailResponse {
            private Long postId;
            private String content;
            private String authorNickname;
            private String profileImage;
            private int likeCount;
            private int commentCount;
            private List<String> imageUrls;
            private List<FamilyTagResponse> taggedUsers;
            private List<CommentDTO.CommentResponse.GetCommentResponse> comments;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FamilyTagResponse {
            private Long userId;
            private String nickname;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CreatePostResponse {
            private Long postId;
            private LocalDateTime createdAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class GetMyPostListResponse {
            private Long postId;
            private String content;
            private List<FamilyTagResponse> taggedUsers;
            private LocalDateTime createdAt;
            private List<String> imageUrls;
        }
    }
}
