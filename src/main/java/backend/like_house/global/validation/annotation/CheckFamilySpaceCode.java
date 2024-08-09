package backend.like_house.global.validation.annotation;

import backend.like_house.global.validation.validator.CheckFamilySpaceCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CheckFamilySpaceCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckFamilySpaceCode {

    String message() default "코드의 길이는 8~12글자이고, 대소문자와 알파벳으로 이루어져야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
