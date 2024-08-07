package backend.like_house.domain.auth.repository;

import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndSocialType(String email, SocialType socialType);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> deleteByEmailAndSocialType(String email, SocialType socialType);
}
