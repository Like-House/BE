package backend.like_house.domain.user.service;

import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;

public interface UserQueryService {

    UserDTO.GetProfileResponse getUserProfile(User user);

    UserDTO.ChatRoomUserListResponse getUserByChatRoom(Long chatRoomId);
}
