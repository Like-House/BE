package backend.like_house.global.security.filter;

import backend.like_house.domain.user.entity.SocialName;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.AuthException;
import backend.like_house.global.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
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
                SocialName socialName = jwtUtil.extractSocialName(accessToken);

                if (email != null && socialName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // JWT 검증 성공 시 인증 객체 생성
                    Authentication authentication = jwtUtil.getAuthentication(email, socialName);
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
                            String email = jwtUtil.extractEmail(refreshedAccessToken);
                            SocialName socialName = jwtUtil.extractSocialName(refreshedAccessToken);
                            Authentication authentication = jwtUtil.getAuthentication(email, socialName);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + refreshedAccessToken);
                        }
                    } else {
                        throw new AuthException(ErrorStatus.INVALID_TOKEN);
                    }
                } else {
                    throw new AuthException(ErrorStatus._UNAUTHORIZED);
                }
            } catch (JwtException ex) {
                throw new AuthException(ErrorStatus._BAD_REQUEST);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }
}
