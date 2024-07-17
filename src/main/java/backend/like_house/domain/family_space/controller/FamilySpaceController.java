package backend.like_house.domain.family_space.controller;

import static backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceResponse.*;
import static backend.like_house.domain.family_space.dto.FamilySpaceDTO.FamilySpaceRequest.*;

import backend.like_house.domain.family_space.converter.FamilySpaceConverter;
import backend.like_house.domain.family_space.entity.FamilySpace;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/family-space")
public class FamilySpaceController {

    @GetMapping("/check/{userId}")
    @Operation(summary = "가족 공간 초대 링크 확인 API", description = "가족 공간 초대 링크가 유효한지 확인하는 API입니다. "
            + "가족 공간 링크가 존재하면 true를 반환합니다. query string 으로 가족 공간 링크를 주세요.")
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디, path variable 입니다."),
            @Parameter(name = "familySpaceLink", description = "가족 공간 링크, query string 입니다.")
    })
    public ApiResponse<CheckFamilySpaceLinkResponse> checkFamilySpaceLink(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "familySpaceLink") String familySpaceLink
    ) {
        // TODO 초대 링크를 통해 Optional<FamilySpace> 가져오기
        Optional<FamilySpace> familySpace = Optional.of(null);
        return ApiResponse.onSuccess(FamilySpaceConverter.toCheckFamilySpaceLinkResponse(familySpace));
    }

    @GetMapping("/generate/{userId}")
    @Operation(summary = "가족 공간 생성 API", description = "새로운 가족 공간을 생성하는 API입니다. query string 으로 가족 공간 링크를 주세요.")
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디, path variable 입니다."),
            @Parameter(name = "familySpaceLink", description = "가족 공간 링크, query string 입니다.")
    })
    public ApiResponse<NewFamilySpaceResponse> generateNewFamilySpace(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "familySpaceLink") String familySpaceLink
    ) {
        // TODO 다른 가족 공간과 링크 중복되는지 확인
        // TODO 가족 공간 생성 -> 주최자 isRoomManager 변경
        FamilySpace familySpace = null;
        return ApiResponse.onSuccess(FamilySpaceConverter.toNewFamilySpaceResponse(familySpace));
    }

    @PostMapping("/enter")
    @Operation(summary = "가족 공간 입장 API", description = "가족 공간에 입장하는 API입니다.")
    public ApiResponse<EnterFamilySpaceResponse> enterFamilySpace(
            @RequestBody @Valid EnterFamilySpaceRequest request
    ) {
        // TODO 차단되어있는 상태이면 입장 X
        // TODO user에 familySpace 연결
        User user = null;
        FamilySpace familySpace = null;
        return ApiResponse.onSuccess(FamilySpaceConverter.toEnterFamilySpaceResponse(user, familySpace));
    }
}