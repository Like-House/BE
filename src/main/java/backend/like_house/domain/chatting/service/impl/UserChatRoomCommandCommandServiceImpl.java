package backend.like_house.domain.chatting.service.impl;

import backend.like_house.domain.chatting.entity.UserChatRoom;
import backend.like_house.domain.chatting.repository.UserChatRoomRepository;
import backend.like_house.domain.chatting.service.UserChatRoomCommandService;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import backend.like_house.global.error.handler.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserChatRoomCommandCommandServiceImpl implements UserChatRoomCommandService {
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Override
    public void updateLastTime(String email, SocialType socialType, Long chatRoomId) {
        User user = userRepository.findByEmailAndSocialType(email, socialType).orElseThrow(()-> new UserException(ErrorStatus.USER_NOT_FOUND));
        UserChatRoom userChatRoom = userChatRoomRepository.findByChatRoomIdAndUserId(chatRoomId, user.getId()).orElseThrow(()-> new ChatRoomException(ErrorStatus.FIRST_JOIN_CHATROOM));
        userChatRoom.updateLastReadTime();
    }
}
