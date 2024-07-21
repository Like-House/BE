package backend.like_house.domain.family_space.controller;

import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetail;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreview;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreviewList;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/{familySpaceId}/family-emoticon")
    @Operation(summary = "가족 이모 티콘 전체 조회 API", description = "가족 이모 티콘 전체 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다.")
    })
    public ApiResponse<FamilyEmoticonPreviewList> getFamilyEmoticons() {
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/family-emoticon/{familyEmoticonId}")
    @Operation(summary = "가족 이모 티콘 조회 API", description = "가족 이모 티콘 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_EMOTICON4001", description = "존재하지 가족 이모티콘 입니다.")
    })
    public ApiResponse<FamilyEmoticonDetail> getFamilyEmoticon(@PathVariable Long familyEmoticonId) {
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/family-emoticon")
    @Operation(summary = "가족 이모 티콘 생성 API", description = "가족 이모 티콘 생성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다.")
    })
    public ApiResponse<FamilyEmoticonPreview> createFamilyEmoticon(@RequestBody FamilyEmoticonDTO.CreateFamilyEmoticonRequest createFamilyEmoticonRequest) {
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/family-emoticon/{familyEmoticonId}")
    @Operation(summary = "가족 이모 티콘 삭제 API", description = "가족 이모 티콘 삭제 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_EMOTICON4001", description = "존재하지 가족 이모티콘 입니다.")
    })
    public ApiResponse<String> deleteFamilyEmoticon(@PathVariable Long familyEmoticonId) {
        return ApiResponse.onSuccess(null);
    }

}
