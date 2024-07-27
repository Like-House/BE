package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.user_management.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByUserIdAndProfileId(Long userId, Long profileId);
}
