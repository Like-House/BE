package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.converter.FamilySpaceConverter;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.family_space.repository.querydsl.FamilySpaceQueryRepository;
import backend.like_house.domain.family_space.service.FamilySpaceCommandService;
import backend.like_house.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilySpaceCommandServiceImpl implements FamilySpaceCommandService {

    private final FamilySpaceRepository familySpaceRepository;
    private final FamilySpaceQueryRepository familySpaceQueryRepository;

    @Override
    public FamilySpace generateNewFamilySpace(User user) {
        FamilySpace familySpace = FamilySpaceConverter.toNewFamilySpace();
        user.setFamilySpace(familySpace);
        user.setIsRoomManager(Boolean.TRUE);

        return familySpaceRepository.save(familySpace);
    }

    @Override
    public void userConnectWithFamilySpace(User user, FamilySpace familySpace) {
        user.setFamilySpace(familySpace);
    }

    @Override
    @Transactional
    public void depriveRoomManager(User user) {
        user.setIsRoomManager(Boolean.FALSE);
    }

    @Override
    public void deleteFamilySpace(User user) {
        FamilySpace familySpace = user.getFamilySpace();
        familySpaceQueryRepository.deleteAllUserConnectFamilySpace(familySpace);
        familySpaceRepository.deleteById(familySpace.getId());
    }
}
