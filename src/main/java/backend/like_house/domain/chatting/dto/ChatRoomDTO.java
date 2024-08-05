package backend.like_house.domain.chatting.dto;

import backend.like_house.global.common.enums.ChatRoomType;
import backend.like_house.global.validation.annotation.CheckImageKeyName;
import backend.like_house.global.validation.annotation.ExistFamilySpace;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ChatRoomDTO {

    @Getter
    public static class CreateChatRoomRequest {
        @ExistFamilySpace
        private Long familySpaceId;
        private String title;
        @CheckImageKeyName
        private String imageKeyName;
        private ChatRoomType chatRoomType;
        private List<Long> roomParticipantIds;
    }

    @Getter
    public static class UpdateChatRoomRequest {
        private Long chatRoomId;
        private String title;
        @CheckImageKeyName
        private String imageKeyName;
    }

    @Getter
    public static class ExitChatRoomRequest {
        private Long chatRoomId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatRoomResponseList {
        private List<ChatRoomResponse> chatRoomResponses;
        private Long ownerId;
        private Boolean hasNext;
        private Long nextCursor;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatRoomResponse {
        private Long chatRoomId;
        private String title;
        private String imageKeyName;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateChatRoomResponse {
        private Long chatRoomId;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateChatRoomResponse {
        private Long chatRoomId;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }
}
