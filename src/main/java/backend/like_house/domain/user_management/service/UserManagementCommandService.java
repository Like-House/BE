package backend.like_house.domain.user_management.service;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.entity.Custom;

public interface UserManagementCommandService {

    Custom modifyFamilyCustom(User user, Long userId, ModifyFamilyDataRequest request);

    void removeUser(User manager, User removeUser);

    void releaseRemoveUser(User manager, User removeUser);

    void blockUser(User manager, User blockUser);

    void releaseBlockUser(User manager, User blockUser);

    void deleteCustomContactByUser(User blockUser, FamilySpace familySpace);

    void deletePostLikesByUser(User blockUser);

    void deletePostsWithoutTags();

    void deletePostTagByUser(User blockUser);

    void deleteCommentsByUser(User blockUser);

    void deletePostsByUser(User blockUser);
}
