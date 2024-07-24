package backend.like_house.global.validation.validator;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.service.BlockUserQueryService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.NotBlockedUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserNotBlockedValidator implements ConstraintValidator<NotBlockedUser, User> {

    private final BlockUserQueryService blockUserQueryService;

    @Override
    public void initialize(NotBlockedUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        boolean existsByUser = blockUserQueryService.existsByUser(value);

        if (existsByUser){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ALREADY_BLOCKED_USER.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
