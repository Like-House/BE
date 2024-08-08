package backend.like_house.global.oauth2.handler;

import backend.like_house.domain.user.entity.Role;
import backend.like_house.global.oauth2.CustomOAuth2User;
import backend.like_house.global.redis.RedisUtil;
import backend.like_house.global.security.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login Success");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            // 최초 OAuth 로그인 시 Guest
            if (oAuth2User.getRole() == Role.ROLE_GUEST) {
                String accessToken = jwtUtil.generateAccessToken(oAuth2User.getEmail(), oAuth2User.getSocialType());
                jwtUtil.sendAccessAndRefreshToken(response, accessToken, null);
            } else {
                loginSuccess(response, oAuth2User);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtUtil.generateAccessToken(oAuth2User.getEmail(), oAuth2User.getSocialType());
        String refreshToken = jwtUtil.generateRefreshToken(oAuth2User.getEmail(), oAuth2User.getSocialType());

        jwtUtil.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        redisUtil.saveRefreshToken(oAuth2User.getEmail(), oAuth2User.getSocialType(), refreshToken);

        response.sendRedirect("http://localhost:5173");
    }
}
