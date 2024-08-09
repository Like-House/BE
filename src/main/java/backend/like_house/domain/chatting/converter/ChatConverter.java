package backend.like_house.domain.chatting.converter;


import backend.like_house.domain.chatting.dto.ChatDTO;
import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.socket.dto.ChattingDTO.MessageDTO;
import org.springframework.data.domain.Slice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ChatConverter {

    public static Chat toChat(MessageDTO messageDTO, User user, ChatRoom chatRoom) {
        return Chat.builder()
                .content(messageDTO.getContent())
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }

    public static ChatDTO.ChatListResponse toChatResponseList(Slice<Chat> chatSlice, Long nextCursor) {
        List<ChatDTO.ChatResponse> chatResponses = chatSlice
                .stream()
                .map(ChatConverter::toChatResponse)
                .collect(Collectors.toList());

        Collections.reverse(chatResponses);

        return ChatDTO.ChatListResponse.builder()
                .hasNext(chatSlice.hasNext())
                .chatResponseList(chatResponses)
                .nextCursor(nextCursor)
                .build();
    }

    public static ChatDTO.ChatResponse toChatResponse(Chat chat) {
        User user = chat.getUser();

        ChatDTO.SenderDTO senderDTO = ChatDTO.SenderDTO.builder()
                .senderId(user == null ? null : user.getId())
                .senderName(user == null ? "알 수 없음" : user.getName())
                .senderProfile(user == null ? null : user.getProfileImage())
                .build();

        return ChatDTO.ChatResponse.builder()
                .chatId(chat.getId())
                .senderDTO(senderDTO)
                .content(chat.getContent())
                .createAt(chat.getCreatedAt())
                .build();
    }

}
