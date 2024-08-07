package backend.like_house.domain.user_management.service;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.FamilyData;
import java.util.List;

public interface UserManagementQueryService {

    List<FamilyData> findFamilyUser(User user);

    List<FamilyData> findFamilyBlockUser(User user);
}
