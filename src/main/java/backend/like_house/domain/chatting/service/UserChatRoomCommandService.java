package backend.like_house.domain.chatting.service;

import backend.like_house.domain.user.entity.SocialType;

public interface UserChatRoomCommandService {
    void updateLastTime(String email, SocialType socialType, Long chatRoomId);
}
