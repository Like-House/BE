package backend.like_house.domain.chatting.converter;

import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.entity.UserChatRoom;
import backend.like_house.domain.user.entity.User;

import java.time.LocalDateTime;

public class UserChatRoomConverter {

    public static UserChatRoom toUserChatRoom (ChatRoom chatRoom, User user) {
        return UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .lastReadTime(LocalDateTime.now())
                .build();
    }
}
