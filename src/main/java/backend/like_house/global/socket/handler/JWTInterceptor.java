package backend.like_house.global.socket.handler;

import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.global.security.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class JWTInterceptor implements HandshakeInterceptor {
    private final JWTUtil jwtUtil;
    private final SocketUtil socketUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String token = request.getURI().getQuery();


        if (token != null && !token.isEmpty()) {
            // 쿼리 문자열을 파싱하여 "token" 값을 찾습니다.
            String[] queryParams = token.split("&");
            String jwtToken = null;

            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals("token")) {
                    jwtToken = keyValue[1];
                    break;
                }
            }

            if (jwtToken != null && !jwtToken.isEmpty()) {

                try {
                    if (jwtUtil.isTokenExpired(jwtToken)) {
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return false;
                    }
                } catch (JwtException | IllegalArgumentException e) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return false;
                }

                // JWT에서 이메일과 소셜 타입 추출
                String email = jwtUtil.extractEmail(jwtToken);
                SocialType socialName = jwtUtil.extractSocialName(jwtToken);

                // 이미 존재하는 소켓 체크
                if (socketUtil.allAlReadyExistsInAnyChatRoom(email, socialName)) {
                    response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
                    return false;
                }

                // 속성에 이메일과 소셜 타입 추가
                attributes.put("email", email);
                attributes.put("social", socialName);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
