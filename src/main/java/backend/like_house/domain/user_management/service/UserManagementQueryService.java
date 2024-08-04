package backend.like_house.domain.user_management.service;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.FamilyData;
import java.util.List;

public interface UserManagementQueryService {

    boolean existsBlockByUserAndFamilySpace(User user, FamilySpace familySpace);

    List<FamilyData> findFamilyUser(User user);

    List<FamilyData> findFamilyBlockUser(User user);
}
