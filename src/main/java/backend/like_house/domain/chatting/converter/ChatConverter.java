package backend.like_house.domain.chatting.converter;


import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.socket.dto.ChattingDTO.MessageDTO;


public class ChatConverter {

    public static Chat toChat(MessageDTO messageDTO, User user, ChatRoom chatRoom) {
        return Chat.builder()
                .content(messageDTO.getContent())
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }

}
