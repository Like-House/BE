package backend.like_house.global.error.handler;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;

public class FamilySpaceException extends GeneralException {

    public FamilySpaceException(ErrorStatus code) {
        super(code);
    }
}
