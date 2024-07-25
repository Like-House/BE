package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
