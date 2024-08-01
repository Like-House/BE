package backend.like_house.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserDTO {

    @Getter
    public static class updateProfileRequest {

        @Schema(description = "수정할 프로필 이미지 url")
        private String profileImage;

        @Schema(description = "수정할 이름", example = "이름")
        private String name;

        @Schema(description = "수정할 생년월일")
        private LocalDate birthDate;
    }

    @Getter
    public static class changePasswordRequest {
        @NotBlank
        @Schema(description = "기존 비밀번호")
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
}
