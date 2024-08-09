package backend.like_house.domain.notification.dto;

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
        List<NotificationResponseDTO> notificationResponseDTOList;
        Boolean hasNext;
        Long nextCursor;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class NotificationResponseDTO {
        public String sender;
        public String title;
        public String content;
        public LocalDate createAt;
    }
}
