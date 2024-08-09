package backend.like_house.domain.notification.service;

import backend.like_house.domain.notification.controller.NotificationRequestType;
import backend.like_house.domain.notification.dto.NotificationDTO;
import backend.like_house.domain.user.entity.User;

public interface NotificationQueryService {

    NotificationDTO.NotificationResponseListDTO getNotifications(User user, Long familySpaceId, NotificationRequestType notificationRequestType, Long cursor, Integer take);
}
