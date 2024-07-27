package backend.like_house.domain.chatting.service;

public interface UserChatRoomCommandService {
    void updateLastTime(String email, Long chatRoomId);
}
