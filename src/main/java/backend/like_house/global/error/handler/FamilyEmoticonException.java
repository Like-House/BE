package backend.like_house.global.error.handler;

import backend.like_house.global.error.code.BaseErrorCode;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;

public class FamilyEmoticonException extends GeneralException {

    public FamilyEmoticonException(BaseErrorCode code) {
        super(code);
    }
}
