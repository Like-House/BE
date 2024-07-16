package backend.like_house.domain.family_space.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FamilySpaceDTO {

    public static class FamilySpaceRequest {

        @Getter
        public static class EnterFamilySpaceRequest {
            @NotNull
            Long userId;
            @NotNull
            Long familySpaceId;
        }
    }

    public static class FamilySpaceResponse {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CheckFamilySpaceLinkResponse {
            Boolean isValid;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class NewFamilySpaceResponse {
            Long familySpaceId;
            String link;
            LocalDateTime createdAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EnterFamilySpaceResponse {
            Long userId;
            Long familySpaceId;
        }
    }
}
