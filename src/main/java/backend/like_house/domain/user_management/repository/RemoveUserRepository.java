package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.user_management.entity.RemoveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemoveUserRepository extends JpaRepository<RemoveUser, Long> {
}
