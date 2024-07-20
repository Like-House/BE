package backend.like_house.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDTO {
    public static class CommentRequest {

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class CreateCommentRequest {
            @NotNull
            private Long postId;
            private Long parentId;
            @NotNull
            private String content;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class UpdateCommentRequest {
            @NotNull
            private Long commentId;
            @NotNull
            private String content;
        }
    }

    public static class CommentResponse {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CreateCommentResponse {
            private Long commentId;
            private Long parentId;
            private Long userId;
            private String content;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class GetCommentResponse {
            private Long commentId;
            private Long parentId;
            private Long userId;
            private String userNickname;
            private String userProfileImage;
            private String content;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }
    }
}
