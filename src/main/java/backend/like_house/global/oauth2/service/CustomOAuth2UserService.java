package backend.like_house.global.oauth2.service;

import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.oauth2.CustomOAuth2User;
import backend.like_house.global.oauth2.OAuthAttributes;
import backend.like_house.global.s3.dto.AwsDTO;
import backend.like_house.global.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AuthRepository memberRepository;
    private final S3Service s3Service;

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

        String imageUrl = extractAttributes.getOauth2UserInfo().getProfileImage();
        String profileImageKey = getProfileImageKey(extractAttributes.getOauth2UserInfo().getProviderId());

        saveProfileImageToS3(imageUrl, profileImageKey);

        User user = getMember(extractAttributes, socialType, profileImageKey);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                user.getEmail(),
                user.getRole(),
                user.getSocialType(),
                profileImageKey
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

    private User getMember(OAuthAttributes attributes, SocialType socialType, String profileImageKey) {
        return memberRepository.findBySocialTypeAndSocialId(socialType, attributes.getOauth2UserInfo().getProviderId())
                .map(user -> {
                    UserDTO.UpdateProfileRequest updateRequest = UserDTO.UpdateProfileRequest.builder()
                            .name(attributes.getOauth2UserInfo().getName())
                            .imageKeyName(profileImageKey)
                            .birthDate(attributes.getOauth2UserInfo().getBirthDate())
                            .build();

                    user.setUpdateUserProfile(updateRequest);
                    return memberRepository.save(user);
                })
                .orElseGet(() -> saveMember(attributes, profileImageKey));
    }

    private User saveMember(OAuthAttributes attributes, String profileImageKey) {
        User createdMember = attributes.toEntity(attributes.getOauth2UserInfo(), profileImageKey);
        return memberRepository.save(createdMember);
    }

    private void saveProfileImageToS3(String imageUrl, String profileImageKey) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = outputStream.toByteArray();

            AwsDTO.FileUploadRequest uploadRequest = AwsDTO.FileUploadRequest.builder()
                    .fileName(profileImageKey)
                    .fileData(imageBytes)
                    .build();

            s3Service.uploadFile(uploadRequest);
            inputStream.close();
        } catch (IOException e) {
            log.error("프로필 이미지를 S3에 저장하는 중 오류 발생", e);
        }
    }

    private String getProfileImageKey(String providerId) {
        String fileExtension = ".jpg";
        String key = providerId + fileExtension;
        return key;
    }
}
