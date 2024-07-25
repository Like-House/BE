package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.converter.ChatRoomConverter;
import backend.like_house.domain.chatting.converter.UserChatRoomConverter;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomRequest;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.domain.chatting.repository.UserChatRoomRepository;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import backend.like_house.global.error.handler.UserException;
import backend.like_house.global.socket.handler.SocketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomCommandServiceImpl implements ChatRoomCommandService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Override
    public ChatRoom createChatRoom(CreateChatRoomRequest createChatRoomRequest, User user) {
        ChatRoom chatRoom = ChatRoomConverter.toChatRoom(createChatRoomRequest);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        createChatRoomRequest.getRoomParticipantIds().add(user.getId());
        createChatRoomRequest.getRoomParticipantIds().forEach((i)->{
           User chatUser = userRepository.findById(i).orElseThrow(()-> new UserException(ErrorStatus.USER_NOT_FOUND));
           userChatRoomRepository.save(UserChatRoomConverter.toUserChatRoom(savedChatRoom, chatUser));
        });
        return chatRoom;
    }
}
