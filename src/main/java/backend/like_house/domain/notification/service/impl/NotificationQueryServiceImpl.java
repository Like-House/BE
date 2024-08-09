package backend.like_house.domain.notification.service.impl;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.notification.controller.NotificationRequestType;
import backend.like_house.domain.notification.converter.NotificationConverter;
import backend.like_house.domain.notification.dto.NotificationDTO;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.notification.repository.NotificationRepository;
import backend.like_house.domain.notification.service.NotificationQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.enums.NotificationType;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.FamilySpaceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;
    private final FamilySpaceRepository familySpaceRepository;

    @Override
    public NotificationDTO.NotificationResponseListDTO getNotifications(User user, Long familySpaceId, NotificationRequestType notificationRequestType, Long cursor, Integer take) {
        FamilySpace familySpace = familySpaceRepository.findById(familySpaceId).orElseThrow(() -> {
            throw new FamilySpaceException(ErrorStatus.FAMILY_SPACE_NOT_FOUND);
        });

        if (cursor == 1) {
            cursor = Long.MAX_VALUE;
        }



        Slice<Notification> notificationSlice;
        if (notificationRequestType.equals(NotificationRequestType.ALL)) {
            notificationSlice = notificationRepository.findByFamilySpaceAndUserAndIdLessThanOrderByIdDesc(familySpace, user, cursor, PageRequest.of(0, take));
        } else {
            NotificationType notificationType = NotificationType.valueOf(notificationRequestType.toString());
            notificationSlice = notificationRepository.findByDtypeAndFamilySpaceAndUserAndIdLessThanOrderByIdDesc(notificationType, familySpace, user, cursor, PageRequest.of(0, take));
        }

        Long nextCursor = null;
        if (!notificationSlice.isLast()) {
            nextCursor = notificationSlice.toList().get(notificationSlice.toList().size() - 1).getId();
        }

        return NotificationConverter.toNotificationResponseListDTO(notificationSlice, nextCursor);
    }
}
