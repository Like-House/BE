package backend.like_house.domain.notification.repository;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.notification.entity.Notification;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.user = :user AND n.familySpace = :familySpace")
    void deleteByUserAndFamilySpace(@Param("user") User user, @Param("familySpace") FamilySpace familySpace);
}
