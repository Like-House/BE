package backend.like_house.domain.family_space.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FamilyEmoticonDTO {

    @Getter
    public static class CreateFamilyEmoticonRequest {
        private Long familySpaceId;
        private String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FamilyEmoticonPreviewList {
        private List<FamilyEmoticonDTO> familyEmoticonDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FamilyEmoticonPreview {
        private Long familyEmoticonId;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FamilyEmoticonDetail {
        private Long familyEmoticonId;
        private String imageUrl;
    }
}
