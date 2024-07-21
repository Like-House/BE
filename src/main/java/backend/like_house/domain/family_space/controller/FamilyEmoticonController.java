package backend.like_house.domain.family_space.controller;

import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetail;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreview;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreviewList;
import backend.like_house.global.common.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/family-space")
public class FamilyEmoticonController {

    @GetMapping("/{familySpaceId}")
    public ApiResponse<FamilyEmoticonPreviewList> getFamilyEmoticons() {
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/family-emoticon/{familyEmoticonId}")
    public ApiResponse<FamilyEmoticonDetail> getFamilyEmoticon(@PathVariable Long familyEmoticonId) {
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("")
    public ApiResponse<FamilyEmoticonPreview> createFamilyEmoticon(@RequestBody FamilyEmoticonDTO.CreateFamilyEmoticonRequest createFamilyEmoticonRequest) {
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{familyEmoticonId}")
    public ApiResponse<String> deleteFamilyEmoticon(@PathVariable Long familyEmoticonId) {
        return ApiResponse.onSuccess(null);
    }

}
