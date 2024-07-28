package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.dto.ChatDTO;
import backend.like_house.domain.user.entity.User;

public interface ChatQueryService {
    ChatDTO.ChatListResponse getFirstChats(User user, Long chatRoomId);
    ChatDTO.ChatListResponse getChats(User user, Long chatRoomId, Long cursor, Integer take);

}
