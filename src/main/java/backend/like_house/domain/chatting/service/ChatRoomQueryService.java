package backend.like_house.domain.chatting.service;


import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.entity.ChatRoom;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ChatRoomQueryService {

    ChatRoomDTO.ChatRoomResponseList getChatRoomsByUserIdAndFamilySpaceId(Long userId, Long familySpaceId, Long cursor, Integer take);

    Optional<ChatRoom> findChatRoomById(Long chatRoomId);
}
