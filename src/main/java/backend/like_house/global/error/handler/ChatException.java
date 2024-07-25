package backend.like_house.global.error.handler;

import backend.like_house.global.error.code.BaseErrorCode;
import backend.like_house.global.error.exception.GeneralException;

public class ChatException extends GeneralException {
    public ChatException(BaseErrorCode code) {
        super(code);
    }
}
