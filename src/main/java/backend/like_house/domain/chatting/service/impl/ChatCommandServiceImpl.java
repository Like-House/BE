package backend.like_house.domain.chatting.service.impl;

import backend.like_house.domain.chatting.converter.ChatConverter;
import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.repository.ChatRepository;
import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.domain.chatting.service.ChatCommandService;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import backend.like_house.global.error.handler.UserException;
import backend.like_house.global.socket.dto.ChattingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // DB에 넣는 API
    @Transactional
    public Chat saveChat(ChattingDTO.MessageDTO messageDTO, String email, SocialType socialType) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDTO.getChatRoomId()).orElseThrow(()-> new ChatRoomException(ErrorStatus.CHATROOM_NOT_FOUND));

        User user = userRepository.findByEmailAndSocialType(email, socialType).orElseThrow(()-> new UserException(ErrorStatus.USER_NOT_FOUND));

        return chatRepository.save(ChatConverter.toChat(messageDTO, user, chatRoom));
    }
}
