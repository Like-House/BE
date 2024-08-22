package backend.like_house.domain.chatting.converter;

import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomResponseList;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomResponse;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.family_space.entity.FamilySpace;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomConverter {

    public static ChatRoom toChatRoom(ChatRoomDTO.CreateChatRoomRequest createChatRoomRequest, FamilySpace familySpace) {
        return ChatRoom.builder()
                .title(createChatRoomRequest.getTitle())
                .dtype(createChatRoomRequest.getChatRoomType())
                .imageKeyName(createChatRoomRequest.getImageKeyName())
                .familySpace(familySpace)
                .build();
    }

    public static CreateChatRoomResponse toCreateChatRoomResponse(ChatRoom chatRoom) {
        return CreateChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .createAt(chatRoom.getCreatedAt())
                .updateAt(chatRoom.getUpdatedAt())
                .build();
    }

    public static UpdateChatRoomResponse toUpdateChatRoomResponse(ChatRoom chatRoom) {
        return UpdateChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .updateAt(chatRoom.getUpdatedAt())
                .createAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatRoomResponseList toChatRoomResponseList(List<ChatRoomResponse> chatRoomList, Long nextCursor, Boolean hasNext, Long userId) {

        return ChatRoomResponseList.builder()
                .hasNext(hasNext)
                .chatRoomResponses(chatRoomList)
                .nextCursor(nextCursor)
                .ownerId(userId)
                .build();
    }

    public static ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom, String title, String imageKeyName) {
        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .title(title)
                .imageKeyName(imageKeyName)
                .build();
    }
}
