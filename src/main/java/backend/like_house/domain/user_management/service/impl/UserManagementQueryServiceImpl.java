package backend.like_house.domain.user_management.service.impl;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.repository.BlockUserRepository;
import backend.like_house.domain.user_management.service.UserManagementQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserManagementQueryServiceImpl implements UserManagementQueryService {

    private final BlockUserRepository blockUserRepository;

    @Override
    public boolean existsBlockByUserAndFamilySpace(User user, FamilySpace familySpace) {
        return blockUserRepository.existsByUserAndFamilySpace(user, familySpace);
    }
}
