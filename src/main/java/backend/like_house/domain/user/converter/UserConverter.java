package backend.like_house.domain.user.converter;

import backend.like_house.domain.user.dto.UserDTO.*;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;


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
                .profileImage(user.getProfileImage())
                .birthDate(user.getBirthDate())
                .build();
    }
}