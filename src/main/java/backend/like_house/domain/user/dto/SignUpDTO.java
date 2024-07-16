package backend.like_house.domain.user.dto;

import lombok.*;

import java.time.LocalDate;

public class SignUpDTO {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SignUpRequest {
        private String name;
        private String email;
        private String password;
        private LocalDate birthDate;
        private String profileImage;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SignUpResponse {
        private String name;
        private String email;
    }
}
