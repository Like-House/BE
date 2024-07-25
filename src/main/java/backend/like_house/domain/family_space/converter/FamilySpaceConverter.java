package backend.like_house.domain.family_space.converter;

import backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.*;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.Optional;

public class FamilySpaceConverter {

    public static CheckFamilySpaceCodeResponse toCheckFamilySpaceCodeResponse(Optional<FamilySpace> familySpace) {
        return CheckFamilySpaceCodeResponse.builder()
                .familySpaceId(familySpace.map(FamilySpace::getId).orElse(0L))
                .build();
    }

    public static NewFamilySpaceResponse toNewFamilySpaceResponse(FamilySpace familySpace, String code,
                                                                  LocalDateTime expireAt) {
        return NewFamilySpaceResponse.builder()
                .familySpaceId(familySpace.getId())
                .code(code)
                .expireAt(expireAt)
                .createdAt(familySpace.getCreatedAt())
                .build();
    }

    public static EnterFamilySpaceResponse toEnterFamilySpaceResponse(User user, FamilySpace familySpace) {
        return EnterFamilySpaceResponse.builder()
                .userId(user.getId())
                .familySpaceId(familySpace.getId())
                .build();
    }

    public static GetFamilySpaceCodeResponse toGetFamilySpaceCodeResponse(Long familySpaceId, String code,
                                                                          LocalDateTime expireAt) {
        return GetFamilySpaceCodeResponse.builder()
                .familySpaceId(familySpaceId)
                .code(code)
                .expireAt(expireAt)
                .build();
    }

    public static FamilySpace toNewFamilySpace() {
        return FamilySpace.builder()
                .build();
    }
}
