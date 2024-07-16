package backend.like_house.domain.user.dto;

import lombok.*;

public class SignInDTO {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SignInRequest {
        private String email;
        private String password;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SignInResponse {
        private String accessToken;
    }
}
