package backend.like_house.domain.family_space.converter;

import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreview;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.entity.FamilySpace;

public class FamilyEmoticonConverter {

    public static FamilyEmoticon toFamilyEmoticon(FamilyEmoticonDTO.CreateFamilyEmoticonRequest createFamilyEmoticonRequest, FamilySpace familySpace) {
        return FamilyEmoticon.builder()
                .filename(createFamilyEmoticonRequest.getFilename())
                .familySpace(familySpace)
                .build();
    }

    public static FamilyEmoticonPreview toFamilyEmoticonPreview(FamilyEmoticon familyEmoticon) {
        return FamilyEmoticonPreview.builder()
                .familyEmoticonId(familyEmoticon.getId())
                .build();
    }
}
