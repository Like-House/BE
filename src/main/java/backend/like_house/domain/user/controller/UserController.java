package backend.like_house.domain.user.controller;

import backend.like_house.domain.user.converter.UserConverter;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.dto.UserDTO.SettingAlarmResponse;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.service.UserCommandService;
import backend.like_house.domain.user.service.UserQueryService;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 정보 수정", description = "사용자 정보 수정 관련 API입니다.")
@RequestMapping("/api/v0/users")
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @GetMapping("/profile")
    @Operation(summary = "사용자 정보 조회 API", description = "프로필 이미지, 이름, 생년월일 정보를 불러옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<UserDTO.GetProfileResponse> getProfile(@Parameter(hidden = true) @LoginUser User user) {
        return ApiResponse.onSuccess(userQueryService.getUserProfile(user));
    }

    @PatchMapping("/profile")
    @Operation(summary = "사용자 정보 수정 API", description = "프로필 이미지, 이름, 생년월일을 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<UserDTO.GetProfileResponse> updateProfile(@Parameter(hidden = true) @LoginUser User user, @RequestBody @Valid UserDTO.UpdateProfileRequest updateProfileRequest) {
        User requestUser = userCommandService.updateUserProfile(user, updateProfileRequest);
        return ApiResponse.onSuccess(userQueryService.getUserProfile(requestUser));
    }

    @PatchMapping("/password")
    @Operation(summary = "사용자 비밀번호 수정 API", description = "사용자의 비밀번호를 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4009", description = "기존 비밀번호와 동일합니다"),
    })
    public ApiResponse<String> changePassword(@Parameter(hidden = true) @LoginUser User user, @RequestBody @Valid UserDTO.UpdatePasswordRequest changePasswordRequest) {
        userCommandService.updateUserPassword(user, changePasswordRequest);
        return ApiResponse.onSuccess("비밀번호 변경 성공");
    }

    @PatchMapping("/alarms/comments")
    @Operation(summary = "댓글 알림 설정 API", description = "사용자의 댓글 알림이 on이라면 off로 off라면 on으로 수정 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<SettingAlarmResponse> commentAlarmSetting(@Parameter(hidden = true) @LoginUser User user) {
        userCommandService.commentAlarmSetting(user);
        return ApiResponse.onSuccess(UserConverter.toSettingAlarmResponse(user));
    }

    @PatchMapping("/alarms/comment-reply")
    @Operation(summary = "대댓글 알림 설정 API", description = "사용자의 대댓글 알림이 on이라면 off로 off라면 on으로 수정 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<SettingAlarmResponse> commentReplyAlarmSetting(@Parameter(hidden = true) @LoginUser User user) {
        userCommandService.commentReplyAlarmSetting(user);
        return ApiResponse.onSuccess(UserConverter.toSettingAlarmResponse(user));
    }

    @PatchMapping("/alarms/events")
    @Operation(summary = "이벤트 알림 설정 API", description = "사용자의 이벤트 알림이 on이라면 off로 off라면 on으로 수정 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<SettingAlarmResponse> eventAlarmSetting(@Parameter(hidden = true) @LoginUser User user) {
        userCommandService.eventAlarmSetting(user);
        return ApiResponse.onSuccess(UserConverter.toSettingAlarmResponse(user));
    }

    @PatchMapping("/alarms/chats")
    @Operation(summary = "채팅 알림 설정 API", description = "사용자의 채팅 알림이 on이라면 off로 off라면 on으로 수정 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<SettingAlarmResponse> chatAlarmSetting(@Parameter(hidden = true) @LoginUser User user) {
        userCommandService.chatAlarmSetting(user);
        return ApiResponse.onSuccess(UserConverter.toSettingAlarmResponse(user));
    }


}
