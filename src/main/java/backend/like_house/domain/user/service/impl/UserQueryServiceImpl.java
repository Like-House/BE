package backend.like_house.domain.user.service.impl;

import backend.like_house.domain.chatting.repository.ChatRoomRepository;
import backend.like_house.domain.user.converter.UserConverter;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.domain.user.service.UserQueryService;

import java.util.List;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.ChatRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public UserDTO.GetProfileResponse getUserProfile(User user) {
        return UserConverter.toGetProfileResponseDTO(user);
    }

    @Override
    public UserDTO.ChatRoomUserListResponse getUserByChatRoom(Long chatRoomId) {
        if (!chatRoomRepository.existsChatRoomById(chatRoomId)) {
            throw new ChatRoomException(ErrorStatus.CHATROOM_NOT_FOUND);
        }

        List<User> userByChatRoomId = userRepository.getUserByChatRoomId(chatRoomId);
        return UserConverter.toChatRoomUserResponseList(userByChatRoomId);
    }
}
