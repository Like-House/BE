package backend.like_house.global.validation.annotation;

import backend.like_house.global.validation.validator.UserNotBlockedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UserNotBlockedValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlockedUser {

    String message() default "이미 차단된 유저입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
