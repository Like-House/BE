package backend.like_house.domain.notification.repository;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.enums.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Slice<Notification> findByFamilySpaceAndUserAndIdLessThanOrderByIdDesc(FamilySpace familySpace, User user, Long id, Pageable pageable);

    Slice<Notification> findByDtypeAndFamilySpaceAndUserAndIdLessThanOrderByIdDesc(NotificationType dtype, FamilySpace familySpace, User user, Long id, Pageable pageable);
}
