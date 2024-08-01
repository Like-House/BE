package backend.like_house.domain.user.service;

import backend.like_house.domain.user.entity.User;

public interface UserCommandService {
    void commentAlarmSetting(User user);

    void commentReplyAlarmSetting(User user);
}
