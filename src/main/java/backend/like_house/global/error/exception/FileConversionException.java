package backend.like_house.global.error.exception;

import backend.like_house.global.error.code.status.ErrorStatus;

public class FileConversionException extends GeneralException {

    public FileConversionException(ErrorStatus code) {
        super(code);
    }
}