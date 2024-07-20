package backend.like_house.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

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
}
