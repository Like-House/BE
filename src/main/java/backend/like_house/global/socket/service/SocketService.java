package backend.like_house.global.socket.service;


import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.domain.chatting.repository.UserChatRoomRepository;
import backend.like_house.domain.chatting.service.ChatCommandService;
import backend.like_house.domain.chatting.service.UserChatRoomCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatException;
import backend.like_house.global.error.handler.ChatRoomException;
import backend.like_house.global.error.handler.UserException;
import backend.like_house.global.socket.handler.SocketUtil;
import backend.like_house.global.socket.handler.TextHandler;
import backend.like_house.global.socket.dto.ChattingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketService {

    private final SocketUtil socketUtil;
    private final ChatCommandService chatCommandService;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserChatRoomCommandService userChatRoomCommandService;


    // scheduler 로 chatting room 연결이 끊길 시 연결 시키는 거 고려
    public void handleEnter(WebSocketSession session, ChattingDTO.MessageDTO chattingDTO) {
        User user = userRepository.findByEmail(session.getAttributes().get("email").toString()).orElseThrow(()-> new UserException(ErrorStatus.USER_NOT_FOUND));
        if (!chatRoomRepository.existsChatRoomById(chattingDTO.getChatRoomId())) {
            throw new ChatRoomException(ErrorStatus.CHATROOM_NOT_FOUND);
        }

        if (!userChatRoomRepository.existsByChatRoomIdAndUserId(chattingDTO.getChatRoomId(), user.getId())) {
            throw new ChatRoomException(ErrorStatus.NOT_JOIN_CHATROOM);
        }

        if (socketUtil.existAllSessionChatRoom(session)) {
            throw new ChatRoomException(ErrorStatus.ALREADY_JOIN_CHATROOM);
        }
        
        // 롤백 처리
        try {
            TextHandler.chatSessionRoom.get(0L).remove(session);
            socketUtil.createOrJoinSessionChatRoom(chattingDTO.getChatRoomId(), session);
            userChatRoomCommandService.updateLastTime(session.getAttributes().get("email").toString(), chattingDTO.getChatRoomId());
        } catch (Exception e) {
            socketUtil.exitAllSessionChatRoom(session);
            socketUtil.createSessionChatRoom(0L, session);
            e.printStackTrace();
            throw new ChatRoomException(ErrorStatus.FAILED_ENTER_CHATROOM);
        }

        log.info("ChatSessionRoom 현황 : " + TextHandler.chatSessionRoom);
    }

    @Transactional
    public void handleTalk(WebSocketSession session, ChattingDTO.MessageDTO chattingDTO) {
        if (!TextHandler.chatSessionRoom.containsKey(chattingDTO.getChatRoomId()) ||
                !TextHandler.chatSessionRoom.get(chattingDTO.getChatRoomId()).contains(session)) {
            throw new ChatRoomException(ErrorStatus.FIRST_JOIN_CHATROOM);
        }

        // 채팅 방 번호로 해당 하는 채팅방 사람들의 이메일 받아오는 로직
        List<String> emails = userRepository.getEmailByChatRoomId(chattingDTO.getChatRoomId());

        // 채팅 DB에 저장
        chatCommandService.saveChat(chattingDTO, session.getAttributes().get("email").toString());

        // 메시지를 모든 세션에 전달
        socketUtil.sendToUserWithOutMe(session, chattingDTO);
        
        // 대기 방 인원에게 메시지 전달
        socketUtil.sendToUserWithOutInSessionRoom(emails, chattingDTO);
        
        // 밖 인원들 에게 푸시 알림 전달


        log.info("ChatSessionRoom 현황 : " + TextHandler.chatSessionRoom);
    }

    public void handleExit(WebSocketSession session, ChattingDTO.MessageDTO chattingDTO) {
        if (!socketUtil.existSessionChatRoom(chattingDTO.getChatRoomId()) ||
                !socketUtil.alReadyJoinChatRoom(chattingDTO.getChatRoomId(), session)) {
            throw new ChatException(ErrorStatus.FIRST_JOIN_CHATROOM);
        }

        // 롤백 처리
        try {
            TextHandler.chatSessionRoom.get(chattingDTO.getChatRoomId()).remove(session);
            TextHandler.chatSessionRoom.get(0L).add(session);
        } catch (Exception e) {
            socketUtil.exitAllSessionChatRoom(session);
            socketUtil.createSessionChatRoom(chattingDTO.getChatRoomId(), session);
            e.printStackTrace();
            throw new ChatRoomException(ErrorStatus.FAILED_EXIT_CHATROOM);
        }

        log.info("ChatSessionRoom 현황 : " + TextHandler.chatSessionRoom);
    }
}
