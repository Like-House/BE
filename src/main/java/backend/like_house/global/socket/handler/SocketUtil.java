package backend.like_house.global.socket.handler;


import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatException;
import backend.like_house.global.socket.dto.ChattingDTO;
import backend.like_house.global.socket.dto.ChattingDTO.MessageDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static backend.like_house.global.socket.handler.TextHandler.chatSessionRoom;

@Component
public class SocketUtil {

    public void createOrJoinSessionChatRoom(Long roomId, WebSocketSession session) {
        if (existSessionChatRoom(roomId)) {
            joinSessionChatRoom(roomId, session);
        } else {
            createSessionChatRoom(roomId, session);
        }
    }



    public Boolean existSessionChatRoom(Long sessionChatRoomId) {
        return chatSessionRoom.containsKey(sessionChatRoomId);
    }


    public void createSessionChatRoom(Long sessionChatRoomId, WebSocketSession webSocketSession) {
        CopyOnWriteArraySet<WebSocketSession> webSocketSessions = new CopyOnWriteArraySet<>();
        webSocketSessions.add(webSocketSession);
        chatSessionRoom.put(sessionChatRoomId, webSocketSessions);
    }


    public void joinSessionChatRoom(Long sessionChatRoomId, WebSocketSession webSocketSession) {
        chatSessionRoom.get(sessionChatRoomId).add(webSocketSession);
    }


    public void sendToUserWithOutMe(WebSocketSession ownSession, MessageDTO messageDTO) {
        for (WebSocketSession s : chatSessionRoom.get(messageDTO.getChatRoomId())) {
            if (!s.equals(ownSession)) {
                try {
                    s.sendMessage(new TextMessage(messageDTO.getContent()));
                } catch (IOException e) {
                    throw new ChatException(ErrorStatus.CHAT_NOT_SEND);
                }
            }
        }
    }

    public void sendToUserWithOutInSessionRoom(List<String> ourEmails, MessageDTO messageDTO) {
        for (WebSocketSession s : chatSessionRoom.get(0L)) {
            if (ourEmails.contains(s.getAttributes().get("email").toString())) {
                try {
                    s.sendMessage(new TextMessage(messageDTO.getContent()));
                } catch (IOException e) {
                    throw new ChatException(ErrorStatus.CHAT_NOT_SEND);
                }
            }
        }
    }


    public void exitAllSessionChatRoom(WebSocketSession ownSession) {
        chatSessionRoom.forEach((l, s) -> {
            s.remove(ownSession);
        });
    }


    public Boolean alReadyJoinChatRoom(Long sessionChatRoomId, WebSocketSession webSocketSession) {
        return chatSessionRoom.get(sessionChatRoomId).contains(webSocketSession);
    }

    public Boolean existAllSessionChatRoom(WebSocketSession ownSession) {
        Boolean isTrue = false;
        for (Map.Entry<Long, CopyOnWriteArraySet<WebSocketSession>> entry : chatSessionRoom.entrySet()) {
            Long key = entry.getKey();
            CopyOnWriteArraySet<WebSocketSession> sessions = entry.getValue();

            if (key != 0L && sessions.contains(ownSession)) {
               isTrue = true;
               break;
            }
        }
        return isTrue;
    }

    public Boolean allAlReadyExistsInAnyChatRoom(String email) {
        for (CopyOnWriteArraySet<WebSocketSession> sessions : chatSessionRoom.values()) {
            for (WebSocketSession session : sessions) {
                String sessionEmail = (String) session.getAttributes().get("email");
                if (email.equals(sessionEmail)) {
                    return true;
                }
            }
        }
        return false;
    }
}
