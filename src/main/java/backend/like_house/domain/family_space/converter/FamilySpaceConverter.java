package backend.like_house.domain.family_space.converter;

import backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.*;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import java.util.Optional;

public class FamilySpaceConverter {

    public static CheckFamilySpaceCodeResponse toCheckFamilySpaceCodeResponse(Optional<FamilySpace> familySpace) {
        return CheckFamilySpaceCodeResponse.builder()
                .familySpaceId(familySpace.map(FamilySpace::getId).orElse(0L))
                .build();
    }

    public static NewFamilySpaceResponse toNewFamilySpaceResponse(FamilySpace familySpace) {
        return NewFamilySpaceResponse.builder()
                .familySpaceId(familySpace.getId())
                .expireAt(familySpace.getExpireAt())
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
