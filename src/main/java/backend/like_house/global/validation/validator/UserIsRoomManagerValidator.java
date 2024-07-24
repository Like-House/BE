package backend.like_house.global.validation.validator;

import backend.like_house.domain.user.entity.User;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.IsRoomManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UserIsRoomManagerValidator implements ConstraintValidator<IsRoomManager, User> {

    @Override
    public void initialize(IsRoomManager constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if (value.getIsRoomManager().equals(Boolean.FALSE)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.USER_NOT_MANAGER.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
