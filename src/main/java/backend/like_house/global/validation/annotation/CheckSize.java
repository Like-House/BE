package backend.like_house.global.validation.annotation;

import backend.like_house.global.validation.validator.CheckSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CheckSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSize {

    String message() default "올바르지 않은 사이즈입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
