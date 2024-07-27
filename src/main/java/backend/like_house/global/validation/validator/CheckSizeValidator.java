package backend.like_house.global.validation.validator;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.CheckSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckSizeValidator implements ConstraintValidator<CheckSize, Integer> {

    @Override
    public void initialize(CheckSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || value < 1){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.INVALID_SIZE_NUMBER.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
