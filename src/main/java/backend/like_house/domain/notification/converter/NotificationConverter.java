package backend.like_house.domain.notification.converter;

import backend.like_house.domain.notification.dto.NotificationDTO;
import backend.like_house.domain.notification.dto.NotificationDTO.NotificationResponseDTO;
import backend.like_house.domain.notification.dto.NotificationDTO.NotificationResponseListDTO;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.post.entity.Post;
import org.springframework.data.domain.Slice;

import java.util.List;

public class NotificationConverter {

    public static NotificationResponseListDTO toNotificationResponseListDTO(Slice<Notification> notifications, Long nextCursor) {


        List<NotificationResponseDTO> notificationResponseDTOList = notifications.stream().map(NotificationConverter::toNotificationResponseDTO).toList();

        return NotificationResponseListDTO
                .builder()
                .nextCursor(nextCursor)
                .notificationResponseDTOList(notificationResponseDTOList)
                .hasNext(notifications.hasNext())
                .build();
    }

    public static NotificationResponseDTO toNotificationResponseDTO(Notification notification) {
        return NotificationResponseDTO
                .builder()
                .title(notification.getTitle())
                .sender(notification.getUser().getName())
                .createAt(notification.getDate())
                .content(notification.getContent())
                .build();
    }
}
