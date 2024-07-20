package backend.like_house.domain.family_space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
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
            private Long userId;
            @NotNull
            @Schema(description = "입장할 가족 공간 아이디", example = "1")
            private Long familySpaceId;
        }
    }

    public static class FamilySpaceResponse {

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class CheckFamilySpaceCodeResponse {
            @Schema(description = "초대 코드에 해당하는 가족 공간이 존재하면 true", example = "true")
            private Boolean isValid;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class NewFamilySpaceResponse {
            @Schema(description = "새로 만들어진 가족 공간 아이디", example = "1")
            private Long familySpaceId;
            @Schema(description = "새로 만들어진 가족 공간 초대 코드", example = "A1b2C4")
            private String code;
            @Schema(description = "가족 공간이 새로 만들어진 시간")
            private LocalDateTime createdAt;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class EnterFamilySpaceResponse {
            @Schema(description = "입장한 유저 아이디", example = "1")
            private Long userId;
            @Schema(description = "입장한 가족 공간 아이디", example = "1")
            private Long familySpaceId;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class GetFamilySpaceCodeResponse {
            @Schema(description = "가족 공간 아이디", example = "1")
            private Long familySpaceId;
            @Schema(description = "가족 공간 초대 코드", example = "A1b2C4")
            private String code;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ModifyFamilySpaceCodeResponse {
            @Schema(description = "가족 공간 아이디", example = "1")
            private Long familySpaceId;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }
    }
}
