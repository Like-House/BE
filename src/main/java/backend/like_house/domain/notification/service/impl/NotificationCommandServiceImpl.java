package backend.like_house.domain.notification.service.impl;

import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.notification.repository.NotificationRepository;
import backend.like_house.domain.notification.service.NotificationCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.common.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;

    @Override
    public void saveNotification(User sender, User receiver, String title, String content, NotificationType type) {

        Notification notification = Notification.builder()
                .writerId(sender.getId())
                .user(receiver)
                .dtype(type)
                .title(title)
                .content(content)
                .date(LocalDate.now())
                .familySpace(sender.getFamilySpace())
                .build();

        notificationRepository.save(notification);
    }

}
