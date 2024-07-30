package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.Chat;
import org.springframework.data.domain.Slice;

public interface CustomChatRepository {
    Slice<Chat> findChatByChatRoomIdOrderByDesc(
            Long cursor, Integer take, Long chatRoomId
    );

    Slice<Chat> findChatsByLastReadTime(Long userId, Long chatRoomId);
}
