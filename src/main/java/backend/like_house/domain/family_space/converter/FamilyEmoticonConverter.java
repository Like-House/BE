package backend.like_house.domain.family_space.converter;

import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetailListResponse;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetailResponse;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreviewResponse;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.entity.FamilySpace;

import java.util.List;

public class FamilyEmoticonConverter {

    public static FamilyEmoticon toFamilyEmoticon(FamilyEmoticonDTO.CreateFamilyEmoticonRequest createFamilyEmoticonRequest, FamilySpace familySpace) {
        return FamilyEmoticon.builder()
                .filename(createFamilyEmoticonRequest.getImageKeyName())
                .familySpace(familySpace)
                .build();
    }

    public static FamilyEmoticonPreviewResponse toFamilyEmoticonPreview(FamilyEmoticon familyEmoticon) {
        return FamilyEmoticonPreviewResponse.builder()
                .familyEmoticonId(familyEmoticon.getId())
                .build();
    }

    public static FamilyEmoticonDetailListResponse toFamilyEmoticonDetailResponseList(List<FamilyEmoticon> familyEmoticonList) {
        List<FamilyEmoticonDetailResponse> familyEmoticons = familyEmoticonList.stream().map(FamilyEmoticonConverter::toFamilyEmoticonDetailResponse).toList();

        return FamilyEmoticonDetailListResponse.builder()
                .familyEmoticonDTOList(familyEmoticons)
                .build();
    }

    public static FamilyEmoticonDetailResponse toFamilyEmoticonDetailResponse(FamilyEmoticon familyEmoticon) {
        return FamilyEmoticonDetailResponse.builder()
                .familyEmoticonId(familyEmoticon.getId())
                .imageKeyName(familyEmoticon.getFilename())
                .build();
    }
}
