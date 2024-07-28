package backend.like_house.domain.family_space.repository;

import backend.like_house.domain.family_space.entity.FamilySpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilySpaceRepository extends JpaRepository<FamilySpace, Long> {
    boolean existsById(Long id);
}
