package backend.like_house.global.error.handler;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(ErrorStatus code) {
        super(code);
    }
}
