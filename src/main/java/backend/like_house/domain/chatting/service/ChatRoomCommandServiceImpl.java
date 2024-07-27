package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.converter.ChatRoomConverter;
import backend.like_house.domain.chatting.converter.UserChatRoomConverter;
import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomRequest;
import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.entity.UserChatRoom;
import backend.like_house.domain.chatting.repository.ChatRepository;
import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.domain.chatting.repository.UserChatRoomRepository;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import backend.like_house.global.error.handler.UserException;
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
    private final ChatRepository chatRepository;
    private final FamilySpaceRepository familySpaceRepository;

    @Override
    public ChatRoom createChatRoom(CreateChatRoomRequest createChatRoomRequest, User user) {

        FamilySpace familySpace = familySpaceRepository.findById(createChatRoomRequest.getFamilySpaceId()).get();

        // 채팅방 생성
        ChatRoom chatRoom = ChatRoomConverter.toChatRoom(createChatRoomRequest, familySpace);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        createChatRoomRequest.getRoomParticipantIds().add(user.getId());
        createChatRoomRequest.getRoomParticipantIds().forEach((i)->{
           User chatUser = userRepository.findById(i).orElseThrow(()-> new UserException(ErrorStatus.USER_NOT_FOUND));
           userChatRoomRepository.save(UserChatRoomConverter.toUserChatRoom(savedChatRoom, chatUser));
        });

        // 초기 chat 생성
        Chat createChat = Chat.builder()
                                .content(user.getName() + "님이 채팅 방을 생성 하였 습니다.")
                                .user(user)
                                .chatRoom(savedChatRoom)
                                .build();

        chatRepository.save(createChat);

        return chatRoom;
    }

    @Override
    public ChatRoom updateChatRoom(ChatRoomDTO.UpdateChatRoomRequest updateChatRoomRequest) {
        ChatRoom chatRoom = chatRoomRepository.findById(updateChatRoomRequest.getChatRoomId()).orElseThrow(()-> new ChatRoomException(ErrorStatus.CHATROOM_NOT_FOUND));
        chatRoom.updateChatRoom(updateChatRoomRequest);
        return chatRoom;
    }

    @Override
    public void exitChatRoom(ChatRoomDTO.ExitChatRoomRequest exitChatRoomRequest, User user) {
        UserChatRoom userChatRoom = userChatRoomRepository.findByChatRoomIdAndUserId(exitChatRoomRequest.getChatRoomId(),user.getId()).orElseThrow(()->{
            throw new ChatRoomException(ErrorStatus.FIRST_JOIN_CHATROOM);
        });
        userChatRoomRepository.delete(userChatRoom);
    }
}
