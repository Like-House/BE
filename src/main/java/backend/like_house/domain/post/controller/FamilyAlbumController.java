package backend.like_house.domain.post.controller;

import backend.like_house.domain.post.dto.FamilyAlbumDTO.*;
import backend.like_house.domain.post.service.FamilyAlbumQueryService;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0/family-album")
public class FamilyAlbumController {

    private final FamilyAlbumQueryService familyAlbumQueryService;

    @GetMapping("")
    @Operation(summary = "가족 앨범 보기 API", description = "특정 날짜와 태그된 가족 구성원에 따라 필터링된 게시글의 사진을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_ALBUM4001", description = "가족 앨범 조회 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간의 ID, query parameter 입니다."),
            @Parameter(name = "date", description = "선택한 날짜, query parameter 입니다."),
            @Parameter(name = "taggedUserIds", description = "태그된 가족 구성원의 ID 리스트, query parameter 입니다.")
    })
    public ApiResponse<List<AlbumPhotoResponse>> viewFamilyAlbum(
            @RequestParam Long familySpaceId,
            @RequestParam LocalDate date,
            @RequestParam(required = false) List<Long> taggedUserIds
    ) {
        List<AlbumPhotoResponse> response = familyAlbumQueryService.viewFamilyAlbum(familySpaceId, date, taggedUserIds);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "앨범 사진 클릭 API", description = "앨범 사진 클릭 시 해당 게시글의 미리보기 정보를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다.")
    })
    public ApiResponse<PostPreviewResponse> getPostPreview(
            @PathVariable Long postId
    ) {
        PostPreviewResponse response = familyAlbumQueryService.getPostPreview(postId);
        return ApiResponse.onSuccess(response);
    }
}