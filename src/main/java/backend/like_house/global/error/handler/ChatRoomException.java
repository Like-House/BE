package backend.like_house.global.error.handler;

import backend.like_house.global.error.code.BaseErrorCode;
import backend.like_house.global.error.exception.GeneralException;

public class ChatRoomException extends GeneralException {
    public ChatRoomException(BaseErrorCode code) {
        super(code);
    }
}
