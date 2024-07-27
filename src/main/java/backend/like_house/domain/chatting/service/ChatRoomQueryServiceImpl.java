package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.converter.ChatRoomConverter;
import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomResponseList;
import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.repository.ChatRepository;
import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomQueryServiceImpl implements ChatRoomQueryService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Override
    public ChatRoomResponseList getChatRoomsByUserIdAndFamilySpaceId(Long userId, Long familySpaceId, Long cursor, Integer take) {
        Slice<ChatRoom> chatRoomSlice = chatRoomRepository.getChatRoomsByUserIdAndFamilySpaceId(userId, familySpaceId, cursor, take);
        Long nextCursor = findNextCursorByChatRoom(chatRoomSlice.toList().get(take - 1));
        return ChatRoomConverter.toChatRoomResponseList(chatRoomSlice, nextCursor);
    }

    private Long findNextCursorByChatRoom(ChatRoom chatRoom) {
        System.out.println(chatRoom.getId());
        Chat chat = chatRepository.findTopByChatRoomIdOrderByIdDesc(chatRoom.getId()).orElseThrow(()-> new ChatRoomException(ErrorStatus.INVALID_CHATROOM));
        return chat.getId();
    }
}
