package backend.like_house.domain.family_space.converter;

import backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.CheckFamilySpaceLinkResponse;
import backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.EnterFamilySpaceResponse;
import backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.NewFamilySpaceResponse;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import java.util.Optional;

public class FamilySpaceConverter {

    public static CheckFamilySpaceLinkResponse toCheckFamilySpaceLinkResponse(Optional<FamilySpace> familySpace) {
        return CheckFamilySpaceLinkResponse.builder()
                .isValid(familySpace.isPresent())
                .build();
    }

    public static NewFamilySpaceResponse toNewFamilySpaceResponse(FamilySpace familySpace) {
        return NewFamilySpaceResponse.builder()
                .familySpaceId(familySpace.getId())
                .createdAt(familySpace.getCreatedAt())
                .build();
    }

    public static EnterFamilySpaceResponse toEnterFamilySpaceResponse(User user, FamilySpace familySpace) {
        return EnterFamilySpaceResponse.builder()
                .userId(user.getId())
                .familySpaceId(familySpace.getId())
                .build();
    }
}
