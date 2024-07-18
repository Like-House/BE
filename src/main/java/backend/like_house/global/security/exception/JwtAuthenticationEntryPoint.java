package backend.like_house.global.security.exception;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.security.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인증 실패시 예외 처리 (unauthorized)
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseUtil responseUtil;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.info("JwtAuthenticationEntryPoint 진입");

        responseUtil.writeErrorResponse(response, ErrorStatus._UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }

}
