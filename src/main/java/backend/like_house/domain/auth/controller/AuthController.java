package backend.like_house.domain.auth.controller;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.service.AuthCommandService;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입/로그인", description = "회원가입 및 로그인 관련 API입니다.")
@RequestMapping("/api/v0/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "일반 회원가입 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<AuthDTO.SignUpResponse> signUp(@RequestBody AuthDTO.SignUpRequest signUpDTO) {
        return ApiResponse.onSuccess(authCommandService.signUp(signUpDTO));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인 API", description = "일반 로그인 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<AuthDTO.SignInResponse> signIn(@RequestBody AuthDTO.SignInRequest signInDTO) {
        return ApiResponse.onSuccess(authCommandService.signIn(signInDTO));
    }

    @PostMapping("/signout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<?> signOut() {
        // 로그아웃 로직 authauthCommandService.signOut
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원탈퇴 API", description = "회원탈퇴 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "사용자를 찾을 수 없습니다.")
    })
    public ApiResponse<?> deleteAccount(@RequestBody AuthDTO.DeleteAccountRequest deleteAccountRequest) {
        // 탈퇴 로직
        return ApiResponse.onSuccess(null);
    }
}

