package backend.like_house.global.security.filter;

import backend.like_house.global.security.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Request Header 에서 토큰 꺼내기
        String accessToken = resolveToken(request);

        if (accessToken != null) {
            try {
                String email = jwtUtil.extractEmail(accessToken);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // JWT 검증 성공 시 인증 객체 생성
                    Authentication authentication = jwtUtil.getAuthentication(email);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException ex) {
                // AccessToken이 만료된 경우 RefreshToken을 사용하여 AccessToken 갱신
                String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
                    refreshToken = refreshToken.substring(7);

                    // Redis에 refreshToken이 있는지 검증
                    if (jwtUtil.isRefreshTokenValid(refreshToken)) {
                        String refreshedAccessToken = jwtUtil.renewAccessToken(refreshToken);
                        if (refreshedAccessToken != null) {
                            Authentication authentication = jwtUtil.getAuthentication(jwtUtil.extractEmail(refreshedAccessToken));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + refreshedAccessToken);
                        }
                    } else {
                        handleJwtException(response, "유효하지 않은 Refresh Token", HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } else {
                    handleJwtException(response, ex.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (JwtException ex) {
                // 그 외의 JWT 예외 처리
                handleJwtException(response, ex.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

    private void handleJwtException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
