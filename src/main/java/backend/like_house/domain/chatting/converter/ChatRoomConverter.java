package backend.like_house.domain.chatting.converter;

import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomResponse;
import backend.like_house.domain.chatting.entity.ChatRoom;

public class ChatRoomConverter {

    public static ChatRoom toChatRoom(ChatRoomDTO.CreateChatRoomRequest createChatRoomRequest) {
        return ChatRoom.builder()
                .title(createChatRoomRequest.getTitle())
                .dtype(createChatRoomRequest.getChatRoomType())
                .build();
    }

    public static CreateChatRoomResponse toCreateChatRoomResponse(ChatRoom chatRoom) {
        return CreateChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .createAt(chatRoom.getCreatedAt())
                .updateAt(chatRoom.getUpdatedAt())
                .build();
    }
}
