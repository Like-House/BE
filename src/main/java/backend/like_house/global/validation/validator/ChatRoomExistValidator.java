package backend.like_house.global.validation.validator;

import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.service.ChatRoomQueryService;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.validation.annotation.ExistChatRoom;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatRoomExistValidator implements ConstraintValidator<ExistChatRoom, Long> {

    private final ChatRoomQueryService chatRoomQueryService;

    @Override
    public void initialize(ExistChatRoom constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<ChatRoom> target = chatRoomQueryService.findChatRoomById(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CHATROOM_NOT_FOUND.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
