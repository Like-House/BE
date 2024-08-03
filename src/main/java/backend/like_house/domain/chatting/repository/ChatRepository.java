package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long>, CustomChatRepository {
    Optional<Chat> findTopByChatRoomIdOrderByIdDesc(Long chatRoomId);

    @Modifying
    @Query("UPDATE Chat c SET c.user = null WHERE c.user = :user")
    void setChatUserNullByUser(@Param("user") User user);
}
