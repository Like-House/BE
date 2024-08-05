package backend.like_house.global.security.filter;

import backend.like_house.domain.user.entity.SocialType;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Request Header 에서 토큰 꺼내기
        String accessToken = resolveToken(request);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Redis에 해당 accessToken blacklist 여부를 확인
            String blacklist = redisTemplate.opsForValue().get(accessToken);

            // 로그아웃 or 탈퇴가 되어 있지 않다면 정상 진행
            if (blacklist != null) {
                if ("logoutUser".equals(blacklist)) {
                    throw new AuthException(ErrorStatus.LOGOUT_USER_TOKEN);
                } else {
                    throw new AuthException(ErrorStatus.DELETE_USER_TOKEN);
                }
            }

            // 토큰이 만료되었는지 확인
            if (jwtUtil.isTokenExpired(accessToken)) {
                throw new AuthException(ErrorStatus.EXPIRED_TOKEN);
            }

            String email = jwtUtil.extractEmail(accessToken);
            SocialType socialType = jwtUtil.extractSocialName(accessToken);

            // JWT 검증 성공 시 인증 객체 생성
            if (email != null && socialType != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = jwtUtil.getAuthentication(email, socialType);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(ErrorStatus.INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);
    }


    // Request Header 에서 토큰 정보를 꺼내오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }
}