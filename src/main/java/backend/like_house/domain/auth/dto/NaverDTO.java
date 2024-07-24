package backend.like_house.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

public class NaverDTO {

    @Getter
    public static class OAuthToken {
        private String token_type;
        private String access_token;
        private int expires_in;
        private String refresh_token;
    }

    @Getter
    public static class NaverProfile {
        private String resultcode;
        private String message;
        private NaverAccount response;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class NaverAccount {
            private String id;
            private String name;
            private String email;
            private String birthyear;
            private String birthday;
            private String profile_image;
        }
    }


}
