package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.KakaoDTO;

public interface OAuthCommandService {
    KakaoDTO.OAuthToken getOAuthToken(String accessCode);
    KakaoDTO.KakaoProfile getKakaoProfile(KakaoDTO.OAuthToken request);
    KakaoDTO.LogInResponse kakaoLogin(String accessCode);
}
