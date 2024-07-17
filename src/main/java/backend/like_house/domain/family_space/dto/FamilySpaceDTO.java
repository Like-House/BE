package backend.like_house.domain.family_space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
            @Schema(description = "입장할 유저 아이디", example = "1")
            Long userId;
            @NotNull
            @Schema(description = "입장할 가족 공간 아이디", example = "1")
            Long familySpaceId;
        }
    }

    public static class FamilySpaceResponse {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CheckFamilySpaceLinkResponse {
            @Schema(description = "가족 공간 링크가 존재하면 true", example = "true")
            Boolean isValid;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class NewFamilySpaceResponse {
            @Schema(description = "새로 만들어진 가족 공간 아이디", example = "1")
            Long familySpaceId;
            @Schema(description = "새로 만들어진 가족 공간 링크", example = "https://asdfasdf/asdf")
            String link;
            LocalDateTime createdAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EnterFamilySpaceResponse {
            @Schema(description = "입장한 유저 아이디", example = "1")
            Long userId;
            @Schema(description = "입장한 가족 공간 아이디", example = "1")
            Long familySpaceId;
        }
    }
}
