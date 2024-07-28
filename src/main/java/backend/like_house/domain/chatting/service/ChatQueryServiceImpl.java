package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.converter.ChatConverter;
import backend.like_house.domain.chatting.dto.ChatDTO;
import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.chatting.repository.ChatRepository;
import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatQueryServiceImpl implements ChatQueryService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatDTO.ChatListResponse getFirstChats(User user, Long chatRoomId) {
        if (!chatRoomRepository.existsChatRoomById(chatRoomId)) {
            throw new ChatRoomException(ErrorStatus.CHATROOM_NOT_FOUND);
        }

        Slice<Chat> chatSlice = chatRepository.findChatsByLastReadTime(user.getId(), chatRoomId);

        Long nextCursor = null;
        if (!chatSlice.isLast()) {
            nextCursor = chatSlice.toList().get(chatSlice.toList().size() - 1).getId();
        }

        return ChatConverter.toChatResponseList(chatSlice, nextCursor);
    }

    @Override
    public ChatDTO.ChatListResponse getChats(User user, Long chatRoomId, Long cursor, Integer take) {
        if (!chatRoomRepository.existsChatRoomById(chatRoomId)) {
            throw new ChatRoomException(ErrorStatus.CHATROOM_NOT_FOUND);
        }

        Slice<Chat> chatSlice = chatRepository.findChatByChatRoomIdOrderByDesc(cursor, take, chatRoomId);

        Long nextCursor = null;
        if (!chatSlice.isLast()) {
            nextCursor = chatSlice.toList().get(chatSlice.toList().size() - 1).getId();
        }
        return ChatConverter.toChatResponseList(chatSlice, nextCursor);
    }


}
