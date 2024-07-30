package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.ChatRoom;
import org.springframework.data.domain.Slice;

public interface CustomChatRoomRepository {

    Slice<ChatRoom> getChatRoomsByUserIdAndFamilySpaceId(Long userId, Long familySpaceId, Long cursor, Integer take);
}
