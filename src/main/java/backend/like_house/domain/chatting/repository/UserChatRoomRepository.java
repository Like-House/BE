package backend.like_house.domain.chatting.repository;

import backend.like_house.domain.chatting.entity.UserChatRoom;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom,Long> {
    Boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);
    Optional<UserChatRoom> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    @Modifying
    @Query("DELETE FROM UserChatRoom uc WHERE uc.user = :user")
    void deleteByUser(@Param("user") User user);
}
