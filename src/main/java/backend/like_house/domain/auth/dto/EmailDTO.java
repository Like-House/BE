package backend.like_house.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailDTO {

    @Getter
    public static class EmailVerificationRequest {

        @NotBlank
        @Schema(description = "인증 확인할 이메일")
        private String email;

        @NotBlank
        @Schema(description = "인증 확인할 인증 코드")
        private String code;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailSendResponse {

        @Schema(description = "인증 확인할 이메일")
        private String email;

        @Schema(description = "인증 확인할 인증 코드")
        private String code;
    }
}
