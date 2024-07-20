package backend.like_house.domain.family_space.controller;

import static backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.*;
import static backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceRequest.*;

import backend.like_house.domain.family_space.converter.FamilySpaceConverter;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/family-space")
@Tag(name = "가족 공간 관련 컨트롤러")
public class FamilySpaceController {

    @PostMapping("/check/{userId}")
    @Operation(summary = "가족 공간 초대 코드 확인 API", description = "가족 공간 초대 코드가 유효한지 확인하는 API입니다. "
            + "가족 공간 초대 코드가 존재하면 true를 반환합니다. query string 으로 가족 공간 초대 코드를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디, path variable 입니다."),
            @Parameter(name = "familySpaceCode", description = "가족 공간 초대 코드, query string 입니다.")
    })
    public ApiResponse<CheckFamilySpaceCodeResponse> checkFamilySpaceCode(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "familySpaceCode") String familySpaceCode
    ) {
        // TODO 초대 코드를 통해 Optional<FamilySpace> 가져오기
        Optional<FamilySpace> familySpace = Optional.of(null);
        return ApiResponse.onSuccess(FamilySpaceConverter.toCheckFamilySpaceCodeResponse(familySpace));
    }


    @PostMapping("/generate/{userId}")
    @Operation(summary = "가족 공간 생성 API", description = "새로운 가족 공간을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4003", description = "이미 가족 공간에 소속되어 있습니다.")
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디, path variable 입니다.")
    })
    public ApiResponse<NewFamilySpaceResponse> generateNewFamilySpace(
            @PathVariable(name = "userId") Long userId
    ) {
        // TODO 가족 공간 초대 코드 랜덤 생성 (중복X)
        // TODO 가족 공간 생성 -> 주최자 isRoomManager 변경
        FamilySpace familySpace = null;
        return ApiResponse.onSuccess(FamilySpaceConverter.toNewFamilySpaceResponse(familySpace));
    }

    @PostMapping("/enter")
    @Operation(summary = "가족 공간 입장 API", description = "가족 공간에 입장하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다.")
    })
    public ApiResponse<EnterFamilySpaceResponse> enterFamilySpace(
            @RequestBody @Valid EnterFamilySpaceRequest request
    ) {
        // TODO 차단되어있는 상태이면 입장 X
        // TODO user에 familySpace 연결
        User user = null;
        FamilySpace familySpace = null;
        return ApiResponse.onSuccess(FamilySpaceConverter.toEnterFamilySpaceResponse(user, familySpace));
    }

    @GetMapping("/check/code")
    @Operation(summary = "가족 공간 초대 코드 확인 API", description = "가족 공간 초대 코드를 확인하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다.")
    })
    public ApiResponse<GetFamilySpaceCodeResponse> getFamilySpaceCode(@Parameter(hidden = true) @LoginUser User user) {
        // TODO 코드 return
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/")
    @Operation(summary = "가족 공간 삭제 API", description = "가족 공간을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4004", description = "주최자가 아닙니다.")
    })
    public ApiResponse<String> deleteFamilySpace(@Parameter(hidden = true) @LoginUser User user) {
        // TODO 연결된 것 싹 다 삭제
        // TODO 주최자 isRoomManager -> false로 변경
        // TODO 주최자만 가능하도록!
        return ApiResponse.onSuccess("Family space deletion completed");
    }

    @PatchMapping("/modify")
    @Operation(summary = "가족 공간 초대 코드 변경 API", description = "가족 공간의 초대 코드를 변경하는 API입니다. "
            + "query string 으로 가족 공간 초대 코드를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4004", description = "주최자가 아닙니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceCode", description = "가족 공간 초대 코드, query string 입니다.")
    })
    public ApiResponse<ModifyFamilySpaceCodeResponse> modifyFamilySpaceCode(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(name = "familySpaceCode") String familySpaceCode) {
        // TODO 중복 X
        // TODO 주최자만 가능하도록!
        return ApiResponse.onSuccess(null);
    }
}