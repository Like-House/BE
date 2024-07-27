package backend.like_house.domain.user_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserManagementDTO {

    public static class UserManagementRequest {

        @Getter
        public static class ModifyFamilyDataRequest {
            @NotNull
            @Schema(description = "수정할 유저 아이디", example = "1")
            private Long userId;
            @Schema(description = "수정할 별명", example = "엄마")
            private String nickname;
            @Schema(description = "수정할 메모", example = "엄마가 가장 좋아하는 꽃은 장미꽃이다.")
            private String memo;
        }
    }

    public static class UserManagementResponse {

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class FamilyListResponse {
            @Schema(description = "가족 목록")
            private List<FamilyData> familyDataList;
            @Schema(description = "해제 목록")
            private List<FamilyData> removeFamilyDataList;
            @Schema(description = "차단 목록")
            private List<FamilyData> blockFamilyDataList;
            @Schema(description = "가족 명수", example = "20")
            private Integer size;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PUBLIC)
        @AllArgsConstructor(access = AccessLevel.PUBLIC)
        public static class FamilyData {
            @Schema(description = "가족 유저 아이디", example = "1")
            private Long userId;
            @Schema(description = "가족 프로필 사진", example = "https://asdfa")
            private String profileImage;
            @Schema(description = "가족 이름", example = "김다빈")
            private String name;
            @Schema(description = "가족 별명", example = "엄마")
            private String nickname;
            @Schema(description = "가족 메모", example = "엄마가 가장 좋아하는 꽃은 장미꽃이다.")
            private String memo;
            @Schema(description = "주최자이면 true", example = "true")
            private Boolean isManager;
        }

        @Builder
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ModifyFamilyDataResponse {
            @Schema(description = "수정된 가족 유저 아이디", example = "1")
            private Long userId;
            @Schema(description = "수정된 시간")
            private LocalDateTime updatedAt;
        }
    }
}
