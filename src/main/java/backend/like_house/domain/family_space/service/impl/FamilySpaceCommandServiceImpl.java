package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.converter.FamilySpaceConverter;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.family_space.service.FamilySpaceCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilySpaceCommandServiceImpl implements FamilySpaceCommandService {

    private final FamilySpaceRepository familySpaceRepository;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public FamilySpace generateNewFamilySpace(User user) {
        FamilySpace familySpace = FamilySpaceConverter.toNewFamilySpace();
        user.setFamilySpace(familySpace);
        user.setRoomManager();

        return familySpaceRepository.save(familySpace);
    }
}
