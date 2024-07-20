package backend.like_house.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class FamilyAlbumDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AlbumPhotoResponse {
        private Long postId;
        private String imageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostPreviewResponse {
        private Long postId;
        private String authorNickname;
        private String profileImage;
        private String content;
        private LocalDateTime createdAt;
        private List<String> imageUrls;
    }
}