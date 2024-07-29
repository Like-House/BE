package backend.like_house.domain.user_management.repository;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.entity.BlockUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {

    boolean existsByUserAndFamilySpace(User user, FamilySpace familySpace);

    void deleteByUserAndFamilySpace(User user, FamilySpace familySpace);
}
