package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.FamilyData;
import backend.like_house.domain.user_management.repository.BlockUserRepository;
import backend.like_house.domain.user_management.repository.querydsl.UserManagementQueryRepository;
import backend.like_house.domain.user_management.service.UserManagementQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserManagementQueryServiceImpl implements UserManagementQueryService {

    private final BlockUserRepository blockUserRepository;
    private final UserManagementQueryRepository userManagementQueryRepository;

    @Override
    public boolean existsBlockByUserAndFamilySpace(User user, FamilySpace familySpace) {
        return blockUserRepository.existsByUserAndFamilySpace(user, familySpace);
    }

    @Override
    public List<FamilyData> findFamilyUser(User user) {
        return userManagementQueryRepository.findFamilyUser(user);
    }

    @Override
    public List<FamilyData> findFamilyRemoveUser(User user) {
        return userManagementQueryRepository.findFamilyRemoveUser(user);
    }

    @Override
    public List<FamilyData> findFamilyBlockUser(User user) {
        return userManagementQueryRepository.findFamilyBlockUser(user);
    }
}
