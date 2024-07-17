package backend.like_house.domain.user.dto;

import lombok.*;

import java.time.LocalDate;

public class UserDTO {

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
}
