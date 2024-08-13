package backend.like_house.domain.notification.dto;

import backend.like_house.global.common.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class NotificationDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class NotificationResponseListDTO {
        private List<NotificationResponseDTO> notificationResponseDTOList;
        private Boolean hasNext;
        private Long nextCursor;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class NotificationResponseDTO {
        private String title;
        private String content;
        private NotificationType notificationType;
        private LocalDate createAt;
        private String senderName;
        private String profileImage;
    }

}
