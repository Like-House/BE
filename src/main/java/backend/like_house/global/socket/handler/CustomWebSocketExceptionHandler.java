package backend.like_house.global.socket.handler;

import backend.like_house.global.error.handler.ChatException;
import backend.like_house.global.error.handler.ChatRoomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;

@Slf4j
public class CustomWebSocketExceptionHandler extends ExceptionWebSocketHandlerDecorator {

    public CustomWebSocketExceptionHandler(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
            try {
                getDelegate().handleMessage(session, message);
            } catch (ChatException ex) {
                log.info("채팅 관련 에러");
                String errorMessage = String.format("{\"isSuccess\": \"false\", \"code\": \"%s\", \"message\": \"%s\"}", ex.getCode(), ex.getErrorReason().getMessage());
                session.sendMessage(new TextMessage(errorMessage));
            } catch (ChatRoomException ex) {
                log.info("채팅방 관련 에러");
                String errorMessage = String.format("{\"isSuccess\": \"false\", \"code\": \"%s\", \"message\": \"%s\"}", ex.getCode(), ex.getErrorReason().getMessage());
                session.sendMessage(new TextMessage(errorMessage));
            } catch (Exception e) {
                log.info("나머지 에러");
                e.printStackTrace();
                String errorMessage = "{\"isSuccess\": \"false\", \"code\": \"500\", \"message\": \"예외적 오류 서버 로그를 확인 해보 세요.\"}";
                session.sendMessage(new TextMessage(errorMessage));
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.handleMessage(session, message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        SocketUtil socketUtil = new SocketUtil();
        socketUtil.exitAllSessionChatRoom(session);
        super.afterConnectionClosed(session, closeStatus);
    }
}
