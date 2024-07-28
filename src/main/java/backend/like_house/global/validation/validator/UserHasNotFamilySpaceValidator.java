package backend.like_house.global.validation.validator;

import backend.like_house.domain.user.entity.User;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.HasNotFamilySpaceUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UserHasNotFamilySpaceValidator implements ConstraintValidator<HasNotFamilySpaceUser, User> {

    @Override
    public void initialize(HasNotFamilySpaceUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if (value.getFamilySpace() != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ALREADY_BELONG_USER_FAMILY_SPACE.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
