package backend.like_house.domain.family_space.repository;

import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.entity.FamilySpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyEmoticonRepository extends JpaRepository<FamilyEmoticon, Long> {
    List<FamilyEmoticon> findAllByFamilySpaceId(Long familySpaceId);
}
