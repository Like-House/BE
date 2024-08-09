package backend.like_house.domain.post.controller;

import backend.like_house.domain.post.dto.PostDTO.PostResponse.*;
import backend.like_house.domain.post.dto.PostDTO.PostRequest.*;
import backend.like_house.domain.post.service.PostCommandService;
import backend.like_house.domain.post.service.PostQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import backend.like_house.global.validation.annotation.CheckPage;
import backend.like_house.global.validation.annotation.CheckSize;
import backend.like_house.global.validation.validator.CheckPageValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0")
@Tag(name = "게시글 관련 컨트롤러")
public class PostController {

    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;
    private final CheckPageValidator checkPageValidator;

    @GetMapping("/family-space/{familySpaceId}/posts")
    @Operation(summary = "홈 (게시글 조회) API", description = "특정 가족 공간의 게시글을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4001", description = "올바르지 않은 페이징 번호입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SIZE4001", description = "올바르지 않은 사이즈입니다.")
    })
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간의 ID, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다. query string 입니다."),
            @Parameter(name = "size", description = "가져올 게시글의 개수입니다. 1이상의 값으로 주세요. query string 입니다.")
    })
    public ApiResponse<List<GetPostListResponse>> getPostsByFamilySpace(
            @PathVariable Long familySpaceId,
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(required = false, name = "page", defaultValue = "1") @CheckPage Integer page,
            @RequestParam(required = false, name = "size", defaultValue = "10") @CheckSize Integer size
    ) {
        Integer validatedPage = checkPageValidator.validateAndTransformPage(page);
        List<GetPostListResponse> response = postQueryService.getPostsByFamilySpace(familySpaceId, user, validatedPage, size);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 페이지 조회 API", description = "특정 게시글의 상세 페이지를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
    })
    public ApiResponse<GetPostDetailResponse> getPostDetail(
            @PathVariable Long postId,
            @Parameter(hidden = true) @LoginUser User user
    ) {
        GetPostDetailResponse response = postQueryService.getPostDetail(postId, user);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/posts")
    @Operation(summary = "게시글 작성 API", description = "새로운 게시글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4002", description = "게시글 작성 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    public ApiResponse<CreatePostResponse> createPost(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody CreatePostRequest createPostRequest
    ) {
        CreatePostResponse response = postCommandService.createPost(createPostRequest, user);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4003", description = "게시글 수정 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
    })
    public ApiResponse<CreatePostResponse> updatePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody UpdatePostRequest updatePostRequest
    ) {
        CreatePostResponse response = postCommandService.updatePost(postId, updatePostRequest, user);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 게시글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4004", description = "게시글 삭제 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
    })
    public ApiResponse<String> deletePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @LoginUser User user
    ) {
        postCommandService.deletePost(postId, user);
        return ApiResponse.onSuccess("게시글 삭제 성공");
    }

    @GetMapping("/posts/my-posts")
    @Operation(summary = "내가 쓴 글 조회 API", description = "사용자가 작성한 모든 게시글을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4001", description = "존재하지 않는 게시글입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "cursor", description = "커서, 마지막으로 받은 게시글의 ID입니다. query string 입니다."),
            @Parameter(name = "size", description = "가져올 게시글의 개수입니다. 1이상의 값으로 주세요. query string 입니다.")
    })
    public ApiResponse<MyPostCursorDataListResponse> getMyPosts(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(name = "cursor", defaultValue = "9223372036854775807") Long cursor,
            @RequestParam(required = false, name = "size", defaultValue = "10") @CheckSize Integer size) {
        MyPostCursorDataListResponse response = postQueryService.getMyPosts(user, cursor, size);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/posts/{postId}/like")
    @Operation(summary = "게시글 좋아요 누르기 API", description = "사용자가 특정 게시글에 좋아요를 누르는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4005", description = "이미 좋아요를 누른 게시글입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다.")
    })
    public ApiResponse<String> likePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @LoginUser User user
    ) {
        postCommandService.likePost(user, postId);
        return ApiResponse.onSuccess("게시글 좋아요 누르기 성공");
    }

    @DeleteMapping("/posts/{postId}/like")
    @Operation(summary = "게시글 좋아요 취소하기 API", description = "사용자가 특정 게시글에 좋아요를 취소하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST4006", description = "좋아요를 누르지 않은 게시글입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다.")
    })
    public ApiResponse<String> unlikePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @LoginUser User user
    ) {
        postCommandService.unlikePost(user, postId);
        return ApiResponse.onSuccess("게시글 좋아요 취소하기 성공");
    }

    @PutMapping("/posts/{postId}/post-alarm")
    @Operation(summary = "게시물 알림 끄기/켜기 API", description = "사용자가 특정 게시물 알림을 끄거나 켜는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
            @Parameter(name = "enable", description = "게시물 알림 활성화 여부, query parameter 입니다.")
    })
    public ApiResponse<String> togglePostAlarm(
            @PathVariable Long postId,
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam Boolean enable
    ) {
        postCommandService.togglePostAlarm(user, postId, enable);
        return ApiResponse.onSuccess("게시물 알림 끄기/켜기 성공");
    }
}

