package backend.like_house.global.validation.validator;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.CheckCursor;
import backend.like_house.global.validation.annotation.CheckPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckCursorValidator implements ConstraintValidator<CheckCursor, Long> {

    @Override
    public void initialize(CheckCursor constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null || value < 1){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.INVALID_PAGE_NUMBER.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
