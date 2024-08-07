package backend.like_house.domain.family_space.service.impl;

import backend.like_house.domain.family_space.converter.FamilySpaceConverter;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.repository.FamilySpaceRepository;
import backend.like_house.domain.family_space.repository.querydsl.FamilySpaceQueryRepository;
import backend.like_house.domain.family_space.service.FamilySpaceCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.repository.BlockUserRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.UserManagementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilySpaceCommandServiceImpl implements FamilySpaceCommandService {

    private final FamilySpaceRepository familySpaceRepository;
    private final FamilySpaceQueryRepository familySpaceQueryRepository;
    private final BlockUserRepository blockUserRepository;

    @Override
    public FamilySpace generateNewFamilySpace(User user) {
        FamilySpace familySpace = FamilySpaceConverter.toNewFamilySpace();
        user.setFamilySpace(familySpace);
        user.setIsRoomManager(Boolean.TRUE);

        return familySpaceRepository.save(familySpace);
    }

    @Override
    public void userConnectWithFamilySpace(User user, FamilySpace familySpace) {
        if (blockUserRepository.existsByUserAndFamilySpace(user, familySpace)) {
            throw new UserManagementException(ErrorStatus.ALREADY_BLOCKED_USER);
        }
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
