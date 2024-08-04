package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long>, CustomChatRepository {
    Optional<Chat> findTopByChatRoomIdOrderByIdDesc(Long chatRoomId);
}
