package backend.like_house.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoDTO {

    @Getter
    public static class OAuthToken {
        private String token_type;
        private String access_token;
        private int expires_in;
        private String refresh_token;
        private int refresh_token_expires_in;
        private String scope;
    }

    @Getter
    public static class KakaoProfile {
        private Long id;
        private String connected_at;
        private Properties properties;
        private KakaoAccount kakao_account;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Properties {
            private String nickname;
            private String profile_image;
        }
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class KakaoAccount {
            private Profile profile;
            private String email;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Profile {
            private String nickname;
            private String profile_image_url;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogInResponse {
        @Schema(description = "로그인한 유저의 accessToken")
        private String accessToken;
        @Schema(description = "로그인한 유저의 refreshToken")
        private String refreshToken;
    }
}
