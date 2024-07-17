package backend.like_house.global.security.exception;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.security.jwt.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인가 실패시 예외 처리 (forbidden)
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ResponseUtil responseUtil;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info("JwtAccessDeniedHandler 진입");
        responseUtil.writeErrorResponse(response, ErrorStatus.INVALID_ACCESS, HttpStatus.FORBIDDEN);
    }
}
