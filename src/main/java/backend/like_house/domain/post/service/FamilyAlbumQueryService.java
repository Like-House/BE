package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.FamilyAlbumDTO.*;

import java.time.LocalDate;
import java.util.List;

public interface FamilyAlbumQueryService {
    List<AlbumPhotoResponse> viewFamilyAlbum(Long familySpaceId, LocalDate date, List<Long> taggedUserIds);
    PostPreviewResponse getPostPreview(Long postId);
}
