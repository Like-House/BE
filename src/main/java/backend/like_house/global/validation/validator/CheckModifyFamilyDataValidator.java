package backend.like_house.global.validation.validator;

import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.CheckModifyFamilyData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckModifyFamilyDataValidator implements ConstraintValidator<CheckModifyFamilyData, ModifyFamilyDataRequest> {

    @Override
    public void initialize(CheckModifyFamilyData constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ModifyFamilyDataRequest value, ConstraintValidatorContext context) {
        if (value.getNickname() == null && value.getMemo() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.AT_LEAST_ONE_NOT_NULL_FAMILY_DATA.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
