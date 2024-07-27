package backend.like_house.domain.auth.service.impl;

import backend.like_house.domain.auth.converter.AuthConverter;
import backend.like_house.domain.auth.converter.OAuthConverter;
import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.GoogleDTO.*;
import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.auth.service.GoogleCommandService;
import backend.like_house.domain.user.entity.SocialType;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleCommandServiceImpl implements GoogleCommandService {

    @Value("${oauth2.google.client-id}")
    private String client;

    @Value("${oauth2.google.client-secret}")
    private String secret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirect;

    private final AuthRepository authRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;


    @Override
    public OAuthToken getOAuthToken(String accessCode) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        accessCode = URLDecoder.decode(accessCode, StandardCharsets.UTF_8);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", client);
        map.add("code", accessCode);
        map.add("client_secret", secret);
        map.add("redirect_uri", redirect);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                    "https://oauth2.googleapis.com/token",
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
    public GoogleProfile getGoogleProfile(OAuthToken oAuthToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> googleProfileRequest = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        "https://www.googleapis.com/userinfo/v2/me",
                        HttpMethod.GET,
                        googleProfileRequest,
                        String.class
                );

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleProfile googleProfile = null;

        try {
            googleProfile = objectMapper.readValue(response.getBody(), GoogleProfile.class);
        } catch (JsonProcessingException e) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }
        return googleProfile;
    }

    @Override
    public AuthDTO.SignInResponse googleLogin(String accessCode) {
        OAuthToken oAuthToken = getOAuthToken(accessCode);
        GoogleProfile googleProfile = getGoogleProfile(oAuthToken);

        Optional<User> requestUser = authRepository.findByEmailAndSocialType(googleProfile.getEmail(), SocialType.GOOGLE);

        User user = null;
        if (requestUser.isPresent()) {
            // 로그인
            user = requestUser.get();
        } else {
            user = OAuthConverter.toGoogleUser(googleProfile);
            authRepository.save(user);
        }
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), SocialType.GOOGLE);
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), SocialType.GOOGLE);

        // Redis에 RefreshToken 저장
        redisUtil.saveRefreshToken(user.getEmail(), user.getSocialType(), refreshToken);

        return AuthConverter.toSignInResponseDTO(accessToken, refreshToken);

    }
}

