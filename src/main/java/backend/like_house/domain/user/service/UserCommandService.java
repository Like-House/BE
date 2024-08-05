package backend.like_house.domain.user.service;

import backend.like_house.domain.user.dto.UserDTO.*;
import backend.like_house.domain.user.entity.User;

public interface UserCommandService {
    User updateUserProfile(User user, UpdateProfileRequest request);
    
    User updateUserPassword(User user, UpdatePasswordRequest request);
    
    void commentAlarmSetting(User user);

    void commentReplyAlarmSetting(User user);

    void eventAlarmSetting(User user);

    void chatAlarmSetting(User user);
}