package backend.like_house.global.oauth2.service;

import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.oauth2.CustomOAuth2User;
import backend.like_house.global.oauth2.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AuthRepository memberRepository;

    private static final String NAVER = "naver";
    private static final String GOOGLE = "google";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        User createdMember = getMember(extractAttributes, socialType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole().toString())), attributes,
                extractAttributes.getNameAttributeKey(), createdMember.getEmail(), createdMember.getRole(), createdMember.getSocialType()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if (NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if (GOOGLE.equals(registrationId)) {
            return SocialType.GOOGLE;
        }
        return SocialType.KAKAO;
    }

    private User getMember(OAuthAttributes attributes, SocialType socialType) {
        User findMember = memberRepository.findBySocialTypeAndSocialId(socialType,
                attributes.getOauth2UserInfo().getProviderId()).orElse(null);

        if (findMember == null) {
            return saveMember(attributes);
        }
        return findMember;
    }

    private User saveMember(OAuthAttributes attributes) {
        User createdMember = attributes.toEntity(attributes.getOauth2UserInfo());
        return memberRepository.save(createdMember);
    }


}