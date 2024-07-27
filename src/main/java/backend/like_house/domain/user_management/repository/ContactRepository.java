package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.user_management.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
