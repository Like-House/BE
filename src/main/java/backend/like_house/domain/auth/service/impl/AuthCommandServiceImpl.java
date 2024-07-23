package backend.like_house.domain.auth.service.impl;

import backend.like_house.domain.auth.converter.AuthConverter;
import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.domain.auth.service.AuthCommandService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;
import backend.like_house.global.redis.RedisUtil;
import backend.like_house.global.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public AuthDTO.SignUpResponse signUp(AuthDTO.SignUpRequest signUpRequest) {
        // 이메일 중복 검사
        authRepository.findByEmail(signUpRequest.getEmail())
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
        // 이메일로 사용자 조회
        User user = authRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.WRONG_PASSWORD);
        }

        // AccessToken & RefreshToken 생성
        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        // Redis에 RefreshToken 저장
        redisUtil.saveRefreshToken(user.getEmail(), refreshToken);

        return AuthConverter.toSignInResponseDTO(accessToken, refreshToken);
    }
}
