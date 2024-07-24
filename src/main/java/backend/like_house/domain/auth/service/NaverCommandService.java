package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.NaverDTO;

public interface NaverCommandService {
    NaverDTO.OAuthToken getOAuthToken(String accessCode);

    NaverDTO.NaverProfile getNaverProfile(NaverDTO.OAuthToken oAuthToken);
    AuthDTO.SignInResponse naverLogin(String accessCode);

}
