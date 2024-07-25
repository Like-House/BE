package backend.like_house.domain.user.repository;

import java.util.List;

public interface CustomUserRepository {
    public List<String> getEmailByChatRoomId(Long chatRoomId);
}
