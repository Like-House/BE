package backend.like_house.domain.auth.controller;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.dto.EmailDTO;
import backend.like_house.domain.auth.service.AuthCommandService;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입/로그인", description = "회원가입 및 로그인 관련 API입니다.")
@RequestMapping("/api/v0/auth")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "일반 회원가입 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "이미 가입된 유저입니다.")
    })
    public ApiResponse<AuthDTO.SignUpResponse> signUp(@RequestBody @Valid AuthDTO.SignUpRequest signUpDTO) {
        return ApiResponse.onSuccess(authCommandService.signUp(signUpDTO));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인 API", description = "일반 로그인 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "시용자를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH4002", description = "비밀번호가 일치하지 않습니다.")

    })
    public ApiResponse<AuthDTO.SignInResponse> signIn(@RequestBody @Valid AuthDTO.SignInRequest signInDTO) {
        return ApiResponse.onSuccess(authCommandService.signIn(signInDTO));
    }

    @PostMapping("/signout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH4001", description = "유효하지 않은 토큰입니다.")

    })
    public ApiResponse<String> signOut(@RequestBody @Valid AuthDTO.TokenRequest tokenRequest) {
        authCommandService.signOut(tokenRequest);
        return ApiResponse.onSuccess("로그아웃 성공");
    }

    @DeleteMapping
    @Operation(summary = "회원탈퇴 API", description = "회원탈퇴 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH4001", description = "유효하지 않은 토큰입니다.")
    })
    public ApiResponse<String> deleteAccount(@RequestBody @Valid AuthDTO.TokenRequest deleteAccountRequest) {
        authCommandService.deleteUser(deleteAccountRequest);
        return ApiResponse.onSuccess("회원 탈퇴 성공");
    }

    @PostMapping("/email/send-verification")
    @Operation(summary="인증 코드 전송 요청 API", description="인증 번호 전송을 요청하는 API 입니다. ")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EMAIL4001", description = "이메일 전송을 실패하였습니다."),
    })
    public ApiResponse<EmailDTO.EmailSendResponse> sendCode(@RequestParam("email") String email) {
        return ApiResponse.onSuccess(authCommandService.sendCode(email));
    }

    @PostMapping("/email/verification")
    @Operation(summary="코드 인증 요청 API", description="이메일 인증 코드 일치 여부를 확인하는 API 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EMAIL4002", description = "인증번호를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "EMAIL4003", description = "인증번호가 일치하지 않습니다.")
    })
    public ApiResponse<String> verifyCode(@RequestBody @Valid EmailDTO.EmailVerificationRequest request) {
        authCommandService.verifyCode(request);
        return ApiResponse.onSuccess("이메일 인증 성공");
    }

}

