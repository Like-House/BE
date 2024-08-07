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
import backend.like_house.global.redis.RedisUtil;
import backend.like_house.global.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AuthDTO.SignInResponse signIn(AuthDTO.SignInRequest signInRequest) {
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

        return AuthConverter.toSignInResponseDTO(accessToken, refreshToken);
    }

    @Override
    public void signOut(AuthDTO.TokenRequest request) {

        processToken(request);

        // 남은 Access Token 유효시간 만큼 redis에 저장
        Long expiration = jwtUtil.getExpiration(request.getAccessToken());
        redisTemplate.opsForValue().set(request.getAccessToken(), "logoutUser", expiration, TimeUnit.MILLISECONDS);

    }

    @Override
    public void deleteUser(AuthDTO.TokenRequest request) {

        processToken(request);

        String email = jwtUtil.extractEmail(request.getAccessToken());
        SocialType socialType = jwtUtil.extractSocialName(request.getAccessToken());

        // 남은 Access Token 유효시간 만큼 redis에 저장
        Long expiration = jwtUtil.getExpiration(request.getAccessToken());
        redisTemplate.opsForValue().set(request.getAccessToken(), "deletedUser", expiration, TimeUnit.MILLISECONDS);

        authRepository.deleteByEmailAndSocialType(email, socialType);
    }

    @Override
    public EmailDTO.EmailSendResponse sendCode(String email) {
        try {
            String code = emailUtil.sendMessage(email);
            return new EmailDTO.EmailSendResponse(email, code);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.EMAIL_SEND_FAIL);
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

    private void processToken(AuthDTO.TokenRequest request) {
        // 로그아웃 or 탈퇴 처리 하고 싶은 토큰이 유효한지 확인
        if (jwtUtil.isTokenExpired(request.getAccessToken())) {
            throw new AuthException(ErrorStatus.INVALID_TOKEN);
        }

        // Redis에 해당 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제
        String email = jwtUtil.extractEmail(request.getAccessToken());
        SocialType socialType = jwtUtil.extractSocialName(request.getAccessToken());

        if (redisTemplate.opsForValue().get(email + ":" + socialType) != null) {
            redisTemplate.delete(email + ":" + socialType);
        }

    }


}
