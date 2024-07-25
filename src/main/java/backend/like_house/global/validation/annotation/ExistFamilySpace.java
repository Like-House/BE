package backend.like_house.global.validation.annotation;

import backend.like_house.global.validation.validator.FamilySpaceExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FamilySpaceExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistFamilySpace {

    String message() default "존재하지 않는 가족 공간 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
