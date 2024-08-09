package backend.like_house.global.validation.validator;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.CheckFamilySpaceCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckFamilySpaceCodeValidator implements ConstraintValidator<CheckFamilySpaceCode, String> {

    private static final int CODE_LENGTH_MIN = 8;
    private static final int CODE_LENGTH_MAX = 12;

    @Override
    public void initialize(CheckFamilySpaceCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() < CODE_LENGTH_MIN || value.length() > CODE_LENGTH_MAX) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.INVALID_FAMILY_SPACE_CODE_PATTERN.toString())
                    .addConstraintViolation();
            return false;
        }

        if (!value.matches("^[A-Za-z0-9]+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.INVALID_FAMILY_SPACE_CODE_PATTERN.toString())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
