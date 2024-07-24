package backend.like_house.domain.auth.converter;

import backend.like_house.domain.auth.dto.KakaoDTO;
import backend.like_house.domain.user.entity.User;


public class OAuthConverter {

    public static User toKakaoUser(String nickname, String profileImg, String email) {
        return User.builder()
                .name(nickname)
                .profileImage(profileImg)
                .email(email)
                .socialName("KAKAO")
                .build();
    }

    public static KakaoDTO.LogInResponse toKakaoLoginResponse(String accessToken, String refreshToken) {
        return KakaoDTO.LogInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
