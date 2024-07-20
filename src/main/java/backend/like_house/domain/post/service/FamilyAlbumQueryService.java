package backend.like_house.domain.post.service;

import backend.like_house.domain.post.dto.FamilyAlbumDTO;

import java.time.LocalDate;
import java.util.List;

public interface FamilyAlbumQueryService {
    List<FamilyAlbumDTO.AlbumPhotoResponse> viewFamilyAlbum(Long familySpaceId, LocalDate date, List<Long> taggedUserIds);
    FamilyAlbumDTO.PostPreviewResponse getPostPreview(Long postId);
}
