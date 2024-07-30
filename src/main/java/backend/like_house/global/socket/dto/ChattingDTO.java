package backend.like_house.global.socket.dto;

import lombok.Getter;

public class ChattingDTO {

    @Getter
    public static class MessageDTO {
        private ChatType chatType;
        private String content;
        private Long chatRoomId;

        public enum ChatType {
            ENTER, TALK, EXIT
        }
    }
}
