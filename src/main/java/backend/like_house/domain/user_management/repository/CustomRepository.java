package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.user_management.entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomRepository extends JpaRepository<Custom, Long> {
    Optional<Custom> findByContactId(Long contactId);
}
