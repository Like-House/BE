package backend.like_house.domain.family_space.service;

import backend.like_house.domain.family_space.entity.FamilySpace;
import java.time.LocalDateTime;
import java.util.Optional;

public interface FamilySpaceQueryService {

    Optional<FamilySpace> findFamilySpaceByCode(String code);

    LocalDateTime findExpirationDateByCode(String code);

    String findFamilySpaceCodeById(Long id);

    String generateFamilySpaceCodeById(Long id);

    Optional<FamilySpace> findFamilySpace(Long id);

    String regenerateFamilySpaceCode(Long id);
}
