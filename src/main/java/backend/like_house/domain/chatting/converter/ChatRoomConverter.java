package backend.like_house.domain.chatting.converter;

import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomResponseList;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomResponse;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.family_space.entity.FamilySpace;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomConverter {

    public static ChatRoom toChatRoom(ChatRoomDTO.CreateChatRoomRequest createChatRoomRequest, FamilySpace familySpace) {
        return ChatRoom.builder()
                .title(createChatRoomRequest.getTitle())
                .dtype(createChatRoomRequest.getChatRoomType())
                .imageUrl(createChatRoomRequest.getImageUrl())
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

    public static ChatRoomResponseList toChatRoomResponseList(Slice<ChatRoom> chatRoomList, Long nextCursor, Long userId) {
        List<ChatRoomResponse> chatRooms = chatRoomList
                .stream()
                .map(ChatRoomConverter::toChatRoomResponse)
                .collect(Collectors.toList());

        return ChatRoomResponseList.builder()
                .hasNext(chatRoomList.hasNext())
                .chatRoomResponses(chatRooms)
                .nextCursor(nextCursor)
                .ownerId(userId)
                .build();
    }

    private static ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .title(chatRoom.getTitle())
                .imageUrl(chatRoom.getImageUrl())
                .build();
    }
}
