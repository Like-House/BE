package backend.like_house.domain.family_space.dto;

import backend.like_house.global.validation.annotation.CheckImageKeyName;
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
        @CheckImageKeyName
        private String imageKeyName;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FamilyEmoticonPreviewListResponse {
        private List<FamilyEmoticonDTO> familyEmoticonDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FamilyEmoticonPreviewResponse {
        private Long familyEmoticonId;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FamilyEmoticonDetailResponse {
        private Long familyEmoticonId;
        private String imageKeyName;
    }
}
