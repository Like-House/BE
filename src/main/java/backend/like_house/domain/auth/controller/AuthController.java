package backend.like_house.domain.auth.controller;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.auth.service.AuthCommandService;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입/로그인", description = "회원가입 및 로그인 관련 API입니다.")
@RequestMapping("/api/v0/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService userCommandService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API")
    public ApiResponse<AuthDTO.SignUpResponse> signUp(@RequestBody AuthDTO.SignUpRequest signUpDTO) {
        return ApiResponse.of(SuccessStatus.SIGNUP_OK, userCommandService.signUp(signUpDTO));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인 API")
    public ApiResponse<AuthDTO.SignInResponse> signIn(@RequestBody AuthDTO.SignInRequest signInDTO) {
        return ApiResponse.of(SuccessStatus.SIGNIN_OK, userCommandService.signIn(signInDTO));
    }
}

