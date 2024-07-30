package backend.like_house.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class AuthDTO {

    @Getter
    public static class SignInRequest {
        @NotBlank
        @Schema(description = "로그인할 유저 이메일", example = "이메일")
        private String email;

        @NotBlank
        @Schema(description = "로그인할 유저 비밀번호", example = "비밀번호")
        private String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInResponse {
        @Schema(description = "로그인한 유저의 accessToken")
        private String accessToken;
        @Schema(description = "로그인한 유저의 refreshToken")
        private String refreshToken;
    }

    @Getter
    public static class SignUpRequest {
        @NotBlank
        @Schema(description = "회원가입할 유저 이름", example = "name")
        private String name;

        @NotBlank
        @Schema(description = "회원가입할 유저 이메일", example = "name@example.com")
        private String email;

        @NotBlank
        @Schema(description = "회원가입할 유저 비밀번호", example = "password123")
        private String password;

        @NotNull
        @Schema(description = "회원가입할 유저 생년월일", example = "2024-07-20")
        private LocalDate birthDate;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResponse {
        @Schema(description = "회원가입한 유저 이름", example = "이름")
        private String name;

        @Schema(description = "회원가입한 유저 이메일", example = "이메일")
        private String email;

        @Schema(description = "회원가입한 유저 프로필 사진", example = "image")
        private String profileImage;
    }

    @Getter
    public static class DeleteAccountRequest {
        @NotNull
        @Schema(description = "탈퇴할 유저 아이디", example = "1")
        private Long userId;
    }

}
