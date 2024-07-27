package backend.like_house.domain.user_management.converter;

import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.*;
import java.util.List;

public class UserManagementConverter {

    public static FamilyListResponse toFamilyListResponse(List<FamilyData> familyUser,
                                                          List<FamilyData> familyRemoveUser,
                                                          List<FamilyData> familyBlockUser) {
        return FamilyListResponse.builder()
                .familyDataList(familyUser)
                .removeFamilyDataList(familyRemoveUser)
                .blockFamilyDataList(familyBlockUser)
                .size(familyUser.size() + familyRemoveUser.size() + familyBlockUser.size())
                .build();
    }
}
