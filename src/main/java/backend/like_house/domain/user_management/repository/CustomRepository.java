package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.domain.user_management.entity.Custom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Custom, Long> {

    Optional<Custom> findByContact(Contact contact);
}
