package backend.like_house.domain.chatting.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ChatDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatListResponse {
        private List<ChatResponse> chatResponseList;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatResponse {
        private Long chatId;
        private String content;
        private Long senderId;
        private Integer unreadUserCount;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }
}
