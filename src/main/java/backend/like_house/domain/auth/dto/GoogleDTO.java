package backend.like_house.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

public class GoogleDTO {
    @Getter
    public static class OAuthToken {
        private String access_token;
        private String expires_in;
        private String id_token;
        private String scope;
        private String token_type;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GoogleProfile {
        private String id;
        private String email;
        private String name;
        private String picture;
    }
}
