package backend.like_house.domain.user.dto;

import backend.like_house.global.validation.annotation.CheckImageKeyName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class UserDTO {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetProfileResponse {
        private String name;
        private String imageKeyName;
        private LocalDate birthDate;
    }

    @Getter
    public static class UpdateProfileRequest {

        @CheckImageKeyName
        @Schema(description = "수정할 프로필 이미지 url")
        private String imageKeyName;

        @Schema(description = "수정할 이름", example = "이름")
        private String name;

        @Schema(description = "수정할 생년월일")
        private LocalDate birthDate;
    }

    @Getter
    public static class UpdatePasswordRequest {
        @NotBlank
        @Schema(description = "기존 비밀번호")
        @CheckImageKeyName
        private String currentPassword;

        @NotBlank
        @Schema(description = "수정할 비밀번호")
        private String newPassword;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SettingAlarmResponse {
        private Long userId;
        private Boolean commentAlarmStatus;
        private Boolean commentReplyAlarmStatus;
        private Boolean eventAlarmStatus;
        private Boolean chatAlarmStatus;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomUserListResponse {
        private List<ChatRoomUserResponse> chatRoomUserResponses;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomUserResponse {
        private Long userId;
        private String username;
        private String userProfile;
    }
}
