package backend.like_house.global.oauth2;

import backend.like_house.domain.user.entity.Role;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.oauth2.userinfo.GoogleOAuth2UserInfo;
import backend.like_house.global.oauth2.userinfo.KakaoOAuth2UserInfo;
import backend.like_house.global.oauth2.userinfo.NaverOAuth2UserInfo;
import backend.like_house.global.oauth2.userinfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if (socialType == SocialType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (socialType == SocialType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .socialType(SocialType.valueOf(oauth2UserInfo.getProvider().toUpperCase()))
                .socialId(oauth2UserInfo.getProviderId())
                .email(oauth2UserInfo.getEmail())
                .name(oauth2UserInfo.getName())
                .profileImage(oauth2UserInfo.getProfileImage())
                .birthDate(oauth2UserInfo.getBirthDate())
                .role(Role.ROLE_USER)
                .build();
    }


}




