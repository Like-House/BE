package backend.like_house.domain.user_management.service;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;

public interface UserManagementQueryService {

    boolean existsBlockByUserAndFamilySpace(User user, FamilySpace familySpace);
}
