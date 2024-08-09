package backend.like_house.domain.family_space.controller;

import static backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.*;

import backend.like_house.domain.family_space.converter.FamilySpaceConverter;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.family_space.service.FamilySpaceCommandService;
import backend.like_house.domain.family_space.service.FamilySpaceQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import backend.like_house.global.validation.annotation.CheckFamilySpaceCode;
import backend.like_house.global.validation.annotation.ExistFamilySpace;
import backend.like_house.global.validation.annotation.HasFamilySpaceUser;
import backend.like_house.global.validation.annotation.HasNotFamilySpaceUser;
import backend.like_house.global.validation.annotation.IsRoomManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v0/family-space")
@Tag(name = "가족 공간 관련 컨트롤러")
public class FamilySpaceController {

    private final FamilySpaceQueryService familySpaceQueryService;
    private final FamilySpaceCommandService familySpaceCommandService;

    @PostMapping("/check")
    @Operation(summary = "가족 공간 초대 코드 유효성 확인 API", description = """
        가족 공간 초대 코드가 유효한지 확인하는 API입니다.
        
        가족 공간 초대 코드가 존재하면 해당 가족 공간 아이디를 반환합니다.
        
        query string 으로 가족 공간 초대 코드를 주세요.
        """)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4005", description = "초대 코드가 유효하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4007", description = "코드의 길이는 8~12글자이고, 대소문자와 알파벳으로 이루어져야 합니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceCode", description = "가족 공간 초대 코드, query string 입니다.")
    })
    public ApiResponse<FamilySpaceIdResponse> checkFamilySpaceCode(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(name = "familySpaceCode") @CheckFamilySpaceCode String familySpaceCode
    ) {
        Optional<FamilySpace> familySpace = familySpaceQueryService.findFamilySpaceByCode(familySpaceCode);
        return ApiResponse.onSuccess(FamilySpaceConverter.toCheckFamilySpaceCodeResponse(familySpace));
    }

    @PostMapping("")
    @Operation(summary = "가족 공간 생성 API", description = "새로운 가족 공간을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4004", description = "이미 가족 공간에 소속되어 있습니다.")
    })
    public ApiResponse<NewFamilySpaceResponse> generateNewFamilySpace(
            @Parameter(hidden = true) @LoginUser @HasNotFamilySpaceUser User user) {
        FamilySpace familySpace = familySpaceCommandService.generateNewFamilySpace(user);
        String familySpaceCode = familySpaceQueryService.generateFamilySpaceCodeById(familySpace.getId());
        LocalDateTime expireAt = familySpaceQueryService.findExpirationDateByCode(familySpaceCode);
        return ApiResponse.onSuccess(
                FamilySpaceConverter.toNewFamilySpaceResponse(familySpace, familySpaceCode, expireAt));
    }

    @PostMapping("/enter/{familySpaceId}")
    @Operation(summary = "가족 공간 입장 API", description = "가족 공간에 입장하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4004", description = "이미 가족 공간에 소속되어 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4006", description = "이미 차단된 유저입니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간 아이디, path variable 입니다.")
    })
    public ApiResponse<EnterFamilySpaceResponse> enterFamilySpace(
            @Parameter(hidden = true) @LoginUser @HasNotFamilySpaceUser User user,
            @PathVariable(name = "familySpaceId") @ExistFamilySpace Long familySpaceId
    ) {
        FamilySpace familySpace = familySpaceQueryService.findFamilySpace(familySpaceId).get();
        familySpaceCommandService.userConnectWithFamilySpace(user, familySpace);
        return ApiResponse.onSuccess(FamilySpaceConverter.toEnterFamilySpaceResponse(user, familySpace));
    }

    @GetMapping("")
    @Operation(summary = "내가 속한 가족 공간 입장 API", description = """
            내가 이미 소속된 가족 공간에 입장하는 API입니다.
                    
            가족 공간 아이디를 반환합니다.
                    
            해당 유저가 가족 공간에 속해 있지 않다면 에러를 반환합니다.
            """)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    public ApiResponse<FamilySpaceIdResponse> getMyFamilySpaceId(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user) {
        return ApiResponse.onSuccess(FamilySpaceConverter.toGetMyFamilySpaceId(user));
    }

    @GetMapping("/code")
    @Operation(summary = "가족 공간 초대 코드 확인 API", description = "가족 공간 초대 코드를 확인하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다.")
    })
    public ApiResponse<GetFamilySpaceCodeResponse> getFamilySpaceCode(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser User user) {
        Long familySpaceId = user.getFamilySpace().getId();
        String familySpaceCode = familySpaceQueryService.findFamilySpaceCodeById(familySpaceId);
        LocalDateTime expireAt = familySpaceQueryService.findExpirationDateByCode(familySpaceCode);
        return ApiResponse.onSuccess(
                FamilySpaceConverter.toGetFamilySpaceCodeResponse(familySpaceId, familySpaceCode, expireAt));
    }

    @DeleteMapping("")
    @Operation(summary = "가족 공간 삭제 API", description = "가족 공간을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4004", description = "주최자가 아닙니다.")
    })
    public ApiResponse<String> deleteFamilySpace(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser @IsRoomManager User user) {
        familySpaceCommandService.deleteFamilySpace(user);
        familySpaceCommandService.depriveRoomManager(user);
        return ApiResponse.onSuccess("Family space deletion completed successfully.");
    }

    @PatchMapping("")
    @Operation(summary = "가족 공간 초대 코드 재발급 API", description = "초대 코드를 재발급하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4003", description = "유저가 해당 가족 공간에 속해 있지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4004", description = "주최자가 아닙니다.")
    })
    public ApiResponse<GetFamilySpaceCodeResponse> regenerateFamilySpaceCode(
            @Parameter(hidden = true) @LoginUser @HasFamilySpaceUser @IsRoomManager User user) {
        Long familySpaceId = user.getFamilySpace().getId();
        String familySpaceCode = familySpaceQueryService.regenerateFamilySpaceCode(familySpaceId);
        LocalDateTime expireAt = familySpaceQueryService.findExpirationDateByCode(familySpaceCode);
        return ApiResponse.onSuccess(
                FamilySpaceConverter.toGetFamilySpaceCodeResponse(familySpaceId, familySpaceCode, expireAt));
    }
}