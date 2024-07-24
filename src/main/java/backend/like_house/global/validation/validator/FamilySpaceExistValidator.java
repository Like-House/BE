package backend.like_house.global.validation.validator;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.service.FamilySpaceQueryService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.ExistFamilySpace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FamilySpaceExistValidator implements ConstraintValidator<ExistFamilySpace, Long> {

    private final FamilySpaceQueryService familySpaceQueryService;

    @Override
    public void initialize(ExistFamilySpace constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<FamilySpace> target = familySpaceQueryService.findFamilySpace(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.FAMILY_SPACE_NOT_FOUND.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
