package backend.like_house.global.validation.validator;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.s3.service.S3Service;
import backend.like_house.global.validation.annotation.CheckImageKeyName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckImageKeyNameValidator implements ConstraintValidator<CheckImageKeyName, String> {

    private final S3Service s3Service;

    @Override
    public void initialize(CheckImageKeyName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (!s3Service.isExistKeyName(value)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.KEYNAME_NOT_FOUND.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
