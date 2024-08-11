package backend.like_house.domain.auth.service.impl;

import backend.like_house.domain.auth.converter.AuthConverter;
import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.EmailDTO;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.auth.service.AuthCommandService;
import backend.like_house.global.email.EmailUtil;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;
import backend.like_house.global.error.handler.AuthException;
import backend.like_house.global.firebase.service.FCMQueryService;
import backend.like_house.global.redis.RedisUtil;
import backend.like_house.global.security.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailUtil emailUtil;
    private final FCMQueryService fcmQueryService;

    @Override
    public AuthDTO.SignUpResponse signUp(AuthDTO.SignUpRequest signUpRequest) {
        // 일반 회원가입 - 이메일 중복 검사
        authRepository.findByEmailAndSocialType(signUpRequest.getEmail(), SocialType.LOCAL)
                .ifPresent(user -> {
                    throw new GeneralException(ErrorStatus.USER_ALREADY_EXIST);
                });

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        // 사용자 생성
        User user = AuthConverter.toUser(signUpRequest, encryptedPassword);
        authRepository.save(user);

        return AuthConverter.toSignUpResponseDTO(user);
    }

    @Override
    public void signIn(HttpServletResponse response, AuthDTO.SignInRequest signInRequest) {
        // 일반 로그인 - 이메일로 사용자 조회
        User user = authRepository.findByEmailAndSocialType(signInRequest.getEmail(), SocialType.LOCAL)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.WRONG_PASSWORD);
        }

        // AccessToken & RefreshToken 생성
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getSocialType());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getSocialType());

        // Redis에 RefreshToken 저장
        redisUtil.saveRefreshToken(user.getEmail(), user.getSocialType(), refreshToken);

        jwtUtil.setCookie(response, "accessToken", accessToken, 1800); // 30분
        jwtUtil.setCookie(response, "refreshToken", refreshToken, 604800); // 1주일
    }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {

        processToken(request, response);

        String accessToken = resolveToken(request);
        Long expiration = jwtUtil.getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken, "logoutUser", expiration, TimeUnit.MILLISECONDS);

        String email = jwtUtil.extractEmail(accessToken);
        SocialType socialType = jwtUtil.extractSocialName(accessToken);

        Optional<User> optionalUser = authRepository.findByEmailAndSocialType(email, socialType);
        optionalUser.ifPresent(user -> authRepository.updateFcmTokenById(user.getId(), null));
    }

    @Override
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {

        processToken(request, response);

        String accessToken = resolveToken(request);
        String email = jwtUtil.extractEmail(accessToken);
        SocialType socialType = jwtUtil.extractSocialName(accessToken);

        Long expiration = jwtUtil.getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken, "deletedUser", expiration, TimeUnit.MILLISECONDS);

        authRepository.deleteByEmailAndSocialType(email, socialType);
    }

    @Override
    public void fcmSave(User user, AuthDTO.FcmRequest tokenRequest) {
        fcmQueryService.isTokenValid(user.getName(), tokenRequest.getFcmToken());
        user.addFcmToken(tokenRequest.getFcmToken());
    }

    @Override
    public EmailDTO.EmailSendResponse sendCode(String email) {
        try {
            String code = emailUtil.sendMessage(email);
            return new EmailDTO.EmailSendResponse(email, code);
        } catch (Exception e) {
            throw new AuthException(ErrorStatus.EMAIL_SEND_FAIL);
        }
    }

    @Override
    public void verifyCode(EmailDTO.EmailVerificationRequest request) {

        Object redisCode = redisUtil.getEmailVerificationCode(request.getEmail());
        if (redisCode == null) {
            throw new AuthException(ErrorStatus.EMAIL_CODE_NOT_FOUND);
        }

        boolean isCodeValid = request.getCode().equals(String.valueOf(redisCode));
        if (isCodeValid) {
            redisUtil.deleteEmailVerificationCode(request.getEmail());
        } else {
            throw new AuthException(ErrorStatus.INCORRECT_EMAIL_CODE);
        }
    }

    private void processToken(HttpServletRequest request, HttpServletResponse response) {
        // 로그아웃 or 탈퇴 처리 하고 싶은 토큰이 유효한지 확인
        String accessToken = resolveToken(request);
        if (accessToken == null || jwtUtil.isTokenExpired(accessToken)) {
            throw new AuthException(ErrorStatus.INVALID_TOKEN);
        }

        // Redis에 해당 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제
        String email = jwtUtil.extractEmail(accessToken);
        SocialType socialType = jwtUtil.extractSocialName(accessToken);

        if (redisTemplate.opsForValue().get(email + ":" + socialType) != null) {
            redisTemplate.delete(email + ":" + socialType);
        }

        jwtUtil.setCookie(response, "accessToken", null, 0);
        jwtUtil.setCookie(response, "refreshToken", null, 0);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = resolveTokenFromCookies(request);
        if (token == null) {
            token = resolveTokenFromHeader(request);
        }
        return token;
    }

    private String resolveTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void fcmSignOut(User user) {
        authRepository.updateFcmTokenById(user.getId(), null);
    }


    private String resolveTokenFromHeader(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

}
