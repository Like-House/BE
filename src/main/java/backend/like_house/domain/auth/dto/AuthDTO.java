package backend.like_house.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class AuthDTO {

    @Getter
    public static class SignInRequest {
        String email;
        String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInResponse {
        String accessToken;
        String refreshToken;
    }

    @Getter
    public static class SignUpRequest {
        String name;
        String email;
        String password;
        LocalDate birthDate;
        String profileImage;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResponse {
        String name;
        String email;
    }

    @Getter
    public static class DeleteAccountRequest {

        @NotNull
        @Schema(description = "탈퇴할 유저 아이디", example = "1")
        private Long userId;
    }

}
