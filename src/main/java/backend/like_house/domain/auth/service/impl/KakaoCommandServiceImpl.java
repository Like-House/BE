package backend.like_house.domain.auth.service.impl;

import backend.like_house.domain.auth.converter.AuthConverter;
import backend.like_house.domain.auth.converter.OAuthConverter;
import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.KakaoDTO;
import backend.like_house.domain.auth.dto.KakaoDTO.OAuthToken;
import backend.like_house.domain.auth.dto.KakaoDTO.KakaoProfile;

import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.auth.service.KakaoCommandService;
import backend.like_house.domain.user.entity.SocialName;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.AuthException;
import backend.like_house.global.redis.RedisUtil;
import backend.like_house.global.security.util.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoCommandServiceImpl implements KakaoCommandService {

    @Value("${oauth2.kakao.client-id}")
    private String client;

    @Value("${KAKAO_CLIENT_SECRET}")
    private String secret;

    @Value("${oauth2.kakao.redirect-uri}")
    private String redirect;

    private final AuthRepository authRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public OAuthToken getOAuthToken(String accessCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", client);
        map.add("redirect_uri", redirect);
        map.add("code", accessCode);
        map.add("client_secret", secret);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }

        return oAuthToken;
    }

    @Override
    public KakaoProfile getKakaoProfile(OAuthToken oAuthToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        "https://kapi.kakao.com/v2/user/me",
                        HttpMethod.GET,
                        kakaoProfileRequest,
                        String.class
                );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }

        return kakaoProfile;
    }

    @Override
    public AuthDTO.SignInResponse kakaoLogin(String accessCode) {

        KakaoDTO.OAuthToken oAuthToken = getOAuthToken(accessCode);
        KakaoDTO.KakaoProfile kakaoProfile = getKakaoProfile(oAuthToken);

        Optional<User> requestUser = authRepository.findByEmailAndSocialName(kakaoProfile.getKakao_account().getEmail(), SocialName.KAKAO);
        User user = null;
        if (requestUser.isPresent()) {
            // 로그인
            user = requestUser.get();
        } else {
            // 회원가입
            user = OAuthConverter.toKakaoUser(
                    kakaoProfile.getKakao_account().getProfile().getNickname(),
                    kakaoProfile.getKakao_account().getProfile().getProfile_image_url(),
                    kakaoProfile.getKakao_account().getEmail());
            authRepository.save(user);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), SocialName.KAKAO);
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), SocialName.KAKAO);

        // Redis에 RefreshToken 저장
        redisUtil.saveRefreshToken(user.getEmail(), user.getSocialName(), refreshToken);

        return AuthConverter.toSignInResponseDTO(accessToken, refreshToken);
    }


}