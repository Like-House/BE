package backend.like_house.domain.user.converter;

import backend.like_house.domain.user.dto.UserDTO.*;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;


public class UserConverter {

    public static UserDTO.SettingAlarmResponse toSettingAlarmResponse(User user){
        return UserDTO.SettingAlarmResponse.builder()
                    .userId(user.getId())
                    .chatAlarmStatus(user.getChatAlarm())
                    .commentAlarmStatus(user.getCommentAlarm())
                    .commentReplyAlarmStatus(user.getCommentReplyAlarm())
                    .eventAlarmStatus(user.getEventAlarm())
                    .build();
    }

    public static GetProfileResponse toGetProfileResponseDTO (User user) {
        return GetProfileResponse.builder()
                .name(user.getName())
                .imageKeyName(user.getProfileImage())
                .birthDate(user.getBirthDate())
                .build();
    }

    public static ChatRoomUserListResponse toChatRoomUserResponseList (List<User> users) {
        List<ChatRoomUserResponse> chatRoomUserResponses = users
                .stream()
                .map(UserConverter::toChatRoomUserResponse)
                .collect(Collectors.toList());

        return ChatRoomUserListResponse
                .builder()
                .chatRoomUserResponses(chatRoomUserResponses)
                .build();
    }

    public static ChatRoomUserResponse toChatRoomUserResponse (User user) {
        return ChatRoomUserResponse
                .builder()
                .userId(user.getId())
                .username(user.getName())
                .userProfile(user.getProfileImage())
                .build();
    }
}