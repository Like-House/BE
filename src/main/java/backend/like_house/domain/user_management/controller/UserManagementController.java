package backend.like_house.domain.user_management.controller;

import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementRequest.ModifyFamilyDataRequest;
import backend.like_house.domain.user_management.dto.UserManagementDTO.UserManagementResponse.*;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/user-management")
@Tag(name = "유저와 유저 관계 관련 컨트롤러")
public class UserManagementController {

    @GetMapping("/")
    @Operation(summary = "가족 목록 확인 API", description = "가족 공간에 속한 가족 목록을 확인하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다.")
    })
    public ApiResponse<FamilyListResponse> getFamilyList(@Parameter(hidden = true) @LoginUser User user) {
        // TODO 가족 공간에 해당하는 가족 목록
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping("/")
    @Operation(summary = "가족 정보 수정 API", description = "가족 별명, 메모를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<ModifyFamilyDataResponse> modifyFamilyData(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody @Valid ModifyFamilyDataRequest request) {
        // TODO contact 테이블에 없다면 contact, custom 테이블에 새로 save
        // TODO contact 테이블에 있다면 custom 테이블에 update
        return ApiResponse.onSuccess(null);
    }
}
