package backend.like_house.global.socket.handler;

import backend.like_house.global.security.util.JWTUtil;
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
        // 이미 검증이 되었기 때문에 상관 x
        if (request.getHeaders().getFirst("Authorization") != null) {
            String token = request.getHeaders().getFirst("Authorization").substring(7);
            String email = jwtUtil.extractEmail(token);
            if (socketUtil.allAlReadyExistsInAnyChatRoom(email)) {
                response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
                return false;
            }
            attributes.put("email", email);
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
