package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.family_space.service.FamilySpaceQueryService;
import backend.like_house.global.redis.RedisUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilySpaceQueryServiceImpl implements FamilySpaceQueryService {

    private final FamilySpaceRepository familySpaceRepository;
    private final RedisUtil redisUtil;

    @Override
    public Optional<FamilySpace> findFamilySpaceByCode(String code) {
        Long familySpaceId = redisUtil.getFamilySpaceIdByCode(code);
        return familySpaceRepository.findById(familySpaceId);
    }

    @Override
    public LocalDateTime findExpirationDateByCode(String code) {
        return redisUtil.getFamilySpaceCodeExpirationByCode(code);
    }

    @Override
    public String findFamilySpaceCodeById(Long id) {
        return redisUtil.getOrGenerateFamilySpaceCodeById(id);
    }

    @Override
    public String generateFamilySpaceCodeById(Long id) {
        return redisUtil.generateFamilySpaceCodeById(id);
    }

    @Override
    public Optional<FamilySpace> findFamilySpace(Long id) {
        return familySpaceRepository.findById(id);
    }

    @Override
    public String regenerateFamilySpaceCode(Long id) {
        return redisUtil.resetFamilySpaceCodeById(id);
    }
}
