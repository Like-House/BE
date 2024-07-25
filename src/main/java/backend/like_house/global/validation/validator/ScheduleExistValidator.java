package backend.like_house.global.validation.validator;

import backend.like_house.domain.schedule.entity.Schedule;
import backend.like_house.domain.schedule.service.ScheduleQueryService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.ExistSchedule;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleExistValidator implements ConstraintValidator<ExistSchedule, Long> {

    private final ScheduleQueryService scheduleQueryService;

    @Override
    public void initialize(ExistSchedule constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<Schedule> target = scheduleQueryService.findSchedule(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.SCHEDULE_NOT_FOUND.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
