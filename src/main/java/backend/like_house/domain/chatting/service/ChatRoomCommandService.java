package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.dto.ChatRoomDTO;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomRequest;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.user.entity.User;

public interface ChatRoomCommandService {
    ChatRoom createChatRoom(CreateChatRoomRequest createChatRoomRequest, User user);
}
