package backend.like_house.domain.post.controller;

import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.service.PostCommandService;
import backend.like_house.domain.post.service.PostQueryService;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0/post")
public class PostController {

    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;

    @GetMapping("/home/{familySpaceId}/{userId}")
    @Operation(summary = "홈 (게시글 조회) API", description = "특정 가족 공간의 게시글을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다."),
            @Parameter(name = "cursor", description = "커서 ID, query parameter 입니다."),
            @Parameter(name = "take", description = "가져올 게시글 수, query parameter 입니다.")
    })
    public ApiResponse<List<GetPostListResponse>> getPostsByFamilySpace(
            @PathVariable Long familySpaceId,
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam int take
    ) {
        List<GetPostListResponse> response = postQueryService.getPostsByFamilySpace(familySpaceId, userId, cursor, take);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/detail/{postId}/{userId}")
    @Operation(summary = "게시글 상세 페이지 조회 API", description = "특정 게시글의 상세 페이지를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<GetPostDetailResponse> getPostDetail(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        GetPostDetailResponse response = postQueryService.getPostDetail(postId, userId);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/family-tags/{familySpaceId}")
    @Operation(summary = "가족 태그 조회 API", description = "특정 가족 공간의 멤버들을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<List<FamilyTagResponse>> getFamilyTags(
            @PathVariable Long familySpaceId,
            @PathVariable Long userId
    ) {
        List<FamilyTagResponse> response = postQueryService.getFamilyTags(familySpaceId, userId);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/{userId}")
    @Operation(summary = "게시글 작성 API", description = "새로운 게시글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4002", description = "게시글 작성 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    public ApiResponse<CreatePostResponse> createPost(
            @PathVariable Long userId,
            @RequestPart("createPostRequest") @Valid CreatePostRequest createPostRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        CreatePostResponse response = postCommandService.createPost(createPostRequest, files, userId);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/{postId}/{userId}")
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4003", description = "게시글 수정 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<CreatePostResponse> updatePost(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestPart("updatePostRequest") @Valid UpdatePostRequest updatePostRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        CreatePostResponse response = postCommandService.updatePost(postId, updatePostRequest, files, userId);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{postId}/{userId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 게시글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4004", description = "게시글 삭제 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postCommandService.deletePost(postId, userId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/my-posts/{userId}")
    @Operation(summary = "내가 쓴 글 조회 API", description = "사용자가 작성한 모든 게시글을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<List<GetMyPostListResponse>> getMyPosts(@PathVariable Long userId) {
        List<GetMyPostListResponse> response = postQueryService.getMyPosts(userId);
        return ApiResponse.onSuccess(response);
    }
}