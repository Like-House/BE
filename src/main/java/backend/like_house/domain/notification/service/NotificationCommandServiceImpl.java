package backend.like_house.domain.notification.service;

import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.notification.repository.NotificationRepository;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.repository.UserRepository;
import backend.like_house.global.common.enums.NotificationType;
import backend.like_house.global.firebase.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationCommandServiceImpl implements NotificationCommandService{
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FcmService fcmService;

    public void saveAndCallNotification (User user, String title, String content, NotificationType type) {

        List<User> users = userRepository.findAllByFamilySpace(user.getFamilySpace());

        users.remove(user);
        
        users.forEach((u)-> {
                    Notification notification = Notification.builder()
                            .writerId(user.getId())
                            .user(u)
                            .dtype(type)
                            .title(title)
                            .content(content)
                            .familySpace(user.getFamilySpace())
                            .build();
                    notificationRepository.save(notification);
                    fcmService.sendNotification(u.getFcmToken(), title, content);
                }
        );
    }
}
