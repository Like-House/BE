package backend.like_house.domain.user.service.impl;

import backend.like_house.domain.user.converter.UserConverter;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.domain.user.service.UserCommandService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;
import backend.like_house.global.redis.RedisUtil;
import backend.like_house.global.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public UserDTO.SignUpResponse signUp(UserDTO.SignUpRequest signUpRequest) {
        // 이메일 중복 검사
        userRepository.findByEmail(signUpRequest.getEmail())
                .ifPresent(user -> {
                    throw new GeneralException(ErrorStatus.USER_ALREADY_EXIST);
                });

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        // 사용자 생성
        User user = UserConverter.toUser(signUpRequest, encryptedPassword);
        userRepository.save(user);

        return UserConverter.toSignUpResponseDTO(user);
    }

    @Override
    public UserDTO.SignInResponse signIn(UserDTO.SignInRequest signInRequest) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(signInRequest.getEmail())
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

        return UserConverter.toSignInResponseDTO(accessToken);
    }
}
