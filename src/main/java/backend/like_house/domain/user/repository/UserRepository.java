package backend.like_house.domain.user.repository;

import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
