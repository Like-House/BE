package backend.like_house.domain.post.converter;

import backend.like_house.domain.post.dto.FamilyAlbumDTO.*;
import backend.like_house.domain.post.entity.Post;

import java.util.List;

public class FamilyAlbumConverter {
    public static AlbumPhotoResponse toAlbumPhotoResponse(Post post, String imageUrl) {
        return AlbumPhotoResponse.builder()
                .postId(post.getId())
                .imageUrl(imageUrl)
                .build();
    }

    public static PostPreviewResponse toPostPreviewResponse(Post post, String authorNickname, String profileImage, List<String> imageUrls) {
        return PostPreviewResponse.builder()
                .postId(post.getId())
                .authorNickname(authorNickname)
                .profileImage(profileImage)
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
    }
}
