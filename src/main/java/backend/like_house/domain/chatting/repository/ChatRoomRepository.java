package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, CustomChatRoomRepository {
    Boolean existsChatRoomById(Long id);
}
