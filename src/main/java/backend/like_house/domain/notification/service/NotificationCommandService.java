package backend.like_house.domain.notification.service;

import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.enums.NotificationType;

import java.util.List;

public interface NotificationCommandService {

    void saveNotification(User sender, User receiver, String title, String content, NotificationType type);
}
