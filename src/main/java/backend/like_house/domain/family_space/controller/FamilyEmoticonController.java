package backend.like_house.domain.family_space.controller;

import backend.like_house.domain.family_space.converter.FamilyEmoticonConverter;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonDetailListResponse;
import backend.like_house.domain.family_space.dto.FamilyEmoticonDTO.FamilyEmoticonPreviewResponse;
import backend.like_house.domain.family_space.entity.FamilyEmoticon;
import backend.like_house.domain.family_space.service.FamilyEmoticonCommandService;
import backend.like_house.domain.family_space.service.FamilyEmoticonQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import backend.like_house.global.validation.annotation.ExistFamilySpace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/family-space")
@RequiredArgsConstructor
@Validated
public class FamilyEmoticonController {

    private final FamilyEmoticonCommandService familyEmoticonCommandService;
    private final FamilyEmoticonQueryService familyEmoticonQueryService;

    @GetMapping("/{familySpaceId}/family-emoticon")
    @Operation(summary = "가족 이모 티콘 전체 조회 API", description = "가족 이모 티콘 전체 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    public ApiResponse<FamilyEmoticonDetailListResponse> getFamilyEmoticons(@Parameter(hidden = true) @LoginUser User user, @PathVariable @ExistFamilySpace Long familySpaceId) {
        FamilyEmoticonDetailListResponse familyEmoticonDetailListResponse = familyEmoticonQueryService.familyEmoticonQueryService(user, familySpaceId);
        return ApiResponse.onSuccess(familyEmoticonDetailListResponse);
    }

    @PostMapping("/family-emoticon")
    @Operation(summary = "가족 이모 티콘 생성 API", description = "가족 이모 티콘 생성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    public ApiResponse<FamilyEmoticonPreviewResponse> createFamilyEmoticon(@Parameter(hidden = true) @LoginUser User user, @RequestBody @Valid FamilyEmoticonDTO.CreateFamilyEmoticonRequest createFamilyEmoticonRequest) {
        FamilyEmoticon familyEmoticon = familyEmoticonCommandService.createFamilyEmoticon(user, createFamilyEmoticonRequest);
        return ApiResponse.onSuccess(FamilyEmoticonConverter.toFamilyEmoticonPreview(familyEmoticon));
    }

    @DeleteMapping("/{familySpaceId}/family-emoticon/{familyEmoticonId}")
    @Operation(summary = "가족 이모 티콘 삭제 API", description = "가족 이모 티콘 삭제 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_EMOTICON4001", description = "존재하지 않는 가족 이모티콘 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    public ApiResponse<String> deleteFamilyEmoticon(@Parameter(hidden = true) @LoginUser User user,@PathVariable @ExistFamilySpace Long familySpaceId, @PathVariable Long familyEmoticonId) {
        familyEmoticonCommandService.deleteFamilyEmoticon(familySpaceId, user,familyEmoticonId);
        return ApiResponse.onSuccess("가족티콘 삭제에 성공하였습니다.");
    }

}
