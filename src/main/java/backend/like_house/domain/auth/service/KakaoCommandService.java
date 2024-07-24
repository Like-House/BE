package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.KakaoDTO;

public interface KakaoCommandService {
    KakaoDTO.OAuthToken getOAuthToken(String accessCode);
    KakaoDTO.KakaoProfile getKakaoProfile(KakaoDTO.OAuthToken request);
    AuthDTO.SignInResponse kakaoLogin(String accessCode);
}
