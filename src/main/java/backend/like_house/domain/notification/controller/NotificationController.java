package backend.like_house.domain.notification.controller;

import backend.like_house.domain.notification.dto.NotificationDTO;
import backend.like_house.domain.notification.dto.NotificationDTO.NotificationResponseListDTO;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.notification.service.NotificationQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/family-space")
public class NotificationController {

    private final NotificationQueryService notificationQueryService;

    @GetMapping("/{familySpaceId}/notifications")
    public ApiResponse<NotificationResponseListDTO> getNotifications(@LoginUser User user, @PathVariable Long familySpaceId, @RequestParam(name = "notificationRequestType") NotificationRequestType notificationRequestType, @RequestParam(name = "cursor") Long cursor, @RequestParam(name = "take") Integer take) {
        NotificationResponseListDTO notificationResponseListDTO = notificationQueryService.getNotifications(user, familySpaceId, notificationRequestType,cursor, take);
        return ApiResponse.onSuccess(notificationResponseListDTO);
    }

}
