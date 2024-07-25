package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom,Long> {
    Boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
