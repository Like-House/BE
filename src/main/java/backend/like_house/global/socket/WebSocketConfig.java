package backend.like_house.global.socket;

import backend.like_house.global.error.exception.alarmClient.service.DiscordClient;
import backend.like_house.global.security.util.JWTUtil;
import backend.like_house.global.socket.handler.CustomWebSocketExceptionHandler;
import backend.like_house.global.socket.handler.JWTInterceptor;
import backend.like_house.global.socket.handler.SocketUtil;
import backend.like_house.global.socket.handler.TextHandler;
import backend.like_house.global.socket.service.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketService socketService;
    private final SocketUtil socketUtil;
    private final JWTUtil jwtUtil;
    private final DiscordClient discordClient;
    private final Environment environment;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketExceptionHandler(new TextHandler(socketService, socketUtil), discordClient, environment), "/chat")
                .addInterceptors(new JWTInterceptor(jwtUtil, socketUtil))
                .setAllowedOrigins("*");
    }
}
