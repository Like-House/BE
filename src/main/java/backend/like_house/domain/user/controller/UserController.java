package backend.like_house.domain.user.controller;

import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 정보 수정", description = "사용자 정보 수정 관련 API입니다.")
@RequestMapping("/api/v0/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @PatchMapping("/profile")
    @Operation(summary = "사용자 정보 수정 API", description = "프로필 이미지, 이름, 생년월일을 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<?> updateProfile(@RequestBody UserDTO.updateProfileRequest updateProfileRequest) {
        return ApiResponse.onSuccess(null);
    }

}
