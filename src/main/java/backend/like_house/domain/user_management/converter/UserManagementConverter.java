package backend.like_house.domain.user_management.converter;

import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.*;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.*;
import backend.like_house.domain.user_management.entity.BlockUser;
import backend.like_house.domain.user_management.entity.Contact;
import backend.like_house.domain.user_management.entity.Custom;
import java.util.List;

public class UserManagementConverter {

    public static FamilyListResponse toFamilyListResponse(List<FamilyData> familyUser,
                                                          List<FamilyData> familyBlockUser) {
        return FamilyListResponse.builder()
                .familyDataList(familyUser)
                .blockFamilyDataList(familyBlockUser)
                .size(familyUser.size() + familyBlockUser.size())
                .build();
    }

    public static ModifyFamilyDataResponse toModifyFamilyDataResponse(Long userId, Custom custom) {
        return ModifyFamilyDataResponse.builder()
                .userId(userId)
                .updatedAt(custom.getUpdatedAt())
                .build();
    }

    public static Contact toContact(User user, Long profileId) {
        return Contact.builder()
                .user(user)
                .familySpace(user.getFamilySpace())
                .profileId(profileId)
                .build();
    }

    public static Custom toCustom(Contact contact, ModifyFamilyDataRequest request) {
        return Custom.builder()
                .contact(contact)
                .nickname(request.getNickname())
                .memo(request.getMemo())
                .build();
    }

    public static BlockUser toBlockUser(User user, FamilySpace familySpace) {
        return BlockUser.builder()
                .user(user)
                .familySpace(familySpace)
                .build();
    }
}
