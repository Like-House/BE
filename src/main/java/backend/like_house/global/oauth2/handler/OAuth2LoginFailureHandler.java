package backend.like_house.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 에러 로그 기록
        log.error("OAuth2 로그인 실패: {}", exception.getMessage());

        // 에러 메시지를 쿼리 파라미터로 추가하여 리디렉션
        String errorUrl = "/login?error=true&exception=" + exception.getMessage();

        // 인증 실패 후 처리
        getRedirectStrategy().sendRedirect(request, response, errorUrl);
    }
}
