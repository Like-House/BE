package backend.like_house.global.error.handler;

import backend.like_house.global.error.code.BaseErrorCode;
import backend.like_house.global.error.exception.GeneralException;

public class UserManagementException extends GeneralException {
    public UserManagementException(BaseErrorCode code) {
        super(code);
    }
}
