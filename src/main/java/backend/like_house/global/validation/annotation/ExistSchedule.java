package backend.like_house.global.validation.annotation;

import backend.like_house.global.validation.validator.ScheduleExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ScheduleExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistSchedule {

    String message() default "존재하지 않는 일정 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
