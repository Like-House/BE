package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.entity.Contact;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByUserAndFamilySpaceAndProfileId(User user, FamilySpace familySpace, Long profileId);

    Optional<Contact> findByUserIdAndProfileId(Long userId, Long profileId);
}
