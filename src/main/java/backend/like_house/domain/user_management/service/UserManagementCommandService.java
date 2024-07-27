package backend.like_house.domain.user_management.service;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.entity.Custom;

public interface UserManagementCommandService {

    Custom modifyFamilyCustom(User user, Long userId, ModifyFamilyDataRequest request);

    void removeUser(User manager, User removeUser);

    void releaseRemoveUser(User manager, User removeUser);

    void blockUser(User manager, User blockUser);

    void releaseBlockUser(User manager, User blockUser);
}
