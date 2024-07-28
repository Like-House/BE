package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.converter.ChatConverter;
import backend.like_house.domain.chatting.dto.ChatDTO;
import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.repository.ChatRepository;
import backend.like_house.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatQueryServiceImpl implements ChatQueryService {

    private final ChatRepository chatRepository;


    @Override
    public ChatDTO.ChatListResponse getChats(User user, Long chatRoomId, Long cursor, Integer take) {
        Slice<Chat> chatSlice;
        if (cursor == -1) {
            chatSlice = chatRepository.findChatsByLastReadTime(user.getId(), chatRoomId);
        } else {
            chatSlice = chatRepository.findChatByChatRoomIdOrderByDesc(cursor, take, chatRoomId);
        }

        Long nextCursor = null;
        if (!chatSlice.isLast()) {
            nextCursor = chatSlice.toList().get(chatSlice.toList().size() - 1).getId();
        }
        return ChatConverter.toChatResponseList(chatSlice, nextCursor);
    }
}
