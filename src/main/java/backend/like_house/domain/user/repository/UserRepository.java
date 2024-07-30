package backend.like_house.domain.user.repository;

import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    Optional<User> findByEmailAndSocialType(String email, SocialType socialType);
}
