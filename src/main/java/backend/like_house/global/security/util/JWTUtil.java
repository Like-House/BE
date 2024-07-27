package backend.like_house.global.security.util;

import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.global.security.principal.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationInMs;
    private final long jwtRefreshExpirationInMs;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisTemplate<String, String> redisTemplate;

    public JWTUtil(@Value("${spring.jwt.secret}") String secretKey,
                   @Value("${spring.jwt.expiration}") long jwtExpirationInMs,
                   @Value("${spring.jwt.refreshExpiration}") long jwtRefreshExpirationInMs,
                   CustomUserDetailsService customUserDetailsService,
                   RedisTemplate redisTemplate) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.jwtRefreshExpirationInMs = jwtRefreshExpirationInMs;
        this.customUserDetailsService = customUserDetailsService;
        this.redisTemplate = redisTemplate;
    }

    public String generateAccessToken(String email, SocialType socialType) {
        return generateToken(email, socialType, jwtExpirationInMs);
    }


    public String generateRefreshToken(String email, SocialType socialType) {
        return generateToken(email, socialType, jwtRefreshExpirationInMs);
    }

    private String generateToken(String email, SocialType socialType, long expirationMs) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationDateTime = now.plusSeconds(expirationMs / 1000);

        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("socialName", socialType.toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expirationDateTime.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public SocialType extractSocialName(String token) {
        return SocialType.valueOf(extractClaim(token, claims -> claims.get("socialName", String.class)));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    // AccessToken 갱신
    public String renewAccessToken(String refreshToken) {
        String email = extractEmail(refreshToken);
        SocialType socialType = extractSocialName(refreshToken);

        if (email != null && !isTokenExpired(refreshToken)) {
            return generateAccessToken(email, socialType);
        }
        return null;
    }

    public Authentication getAuthentication(String email, SocialType socialType) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email + ":" + socialType.toString());
        if (userDetails != null) {
            Collection<SimpleGrantedAuthority> authorities = userDetails.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        }
        return null;
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        String tokenFromRedis = redisTemplate.opsForValue().get(refreshToken);
        return tokenFromRedis != null && tokenFromRedis.equals(refreshToken);
    }
}
