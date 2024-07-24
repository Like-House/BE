package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.GoogleDTO;

public interface GoogleCommandService {
    GoogleDTO.OAuthToken getOAuthToken(String accessCode);
    GoogleDTO.GoogleProfile getGoogleProfile(GoogleDTO.OAuthToken oAuthToken);
    AuthDTO.SignInResponse googleLogin(String accessCode);
}
