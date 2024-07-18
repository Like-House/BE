package backend.like_house.domain.post.controller;

import backend.like_house.domain.account.entity.Custom;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.dto.PostDTO;
import backend.like_house.domain.post.service.PostCommandService;
import backend.like_house.domain.post.service.PostQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;

    @GetMapping("/home/{familySpaceId}/{userId}")
    @Operation(summary = "홈 (게시글 조회) API", description = "특정 가족 공간의 게시글을 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다."),
            @Parameter(name = "cursor", description = "커서 ID, query parameter 입니다."),
            @Parameter(name = "take", description = "가져올 게시글 수, query parameter 입니다.")
    })
    public ApiResponse<List<PostDTO.PostResponse.GetPostListResponse>> getPostsByFamilySpace(
            @PathVariable Long familySpaceId,
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam int take
    ) {
        List<PostDTO.PostResponse.GetPostListResponse> response = postQueryService.getPostsByFamilySpace(familySpaceId, userId, cursor, take);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/get/detail/{postId}/{userId}")
    @Operation(summary = "게시글 상세 페이지 조회 API", description = "특정 게시글의 상세 페이지를 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<PostDTO.PostResponse.GetPostDetailResponse> getPostDetail(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        PostDTO.PostResponse.GetPostDetailResponse response = postQueryService.getPostDetail(postId, userId);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/get/family-tags/{familySpaceId}")
    @Operation(summary = "가족 태그 조회 API", description = "특정 가족 공간의 멤버들을 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "familySpaceId", description = "가족 공간의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<List<PostDTO.PostResponse.FamilyTagResponse>> getFamilyTags(
            @PathVariable Long familySpaceId,
            @PathVariable Long userId
    ) {
        List<PostDTO.PostResponse.FamilyTagResponse> response = postQueryService.getFamilyTags(familySpaceId, userId);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/create/{userId}")
    @Operation(summary = "게시글 작성 API", description = "새로운 게시글을 작성하는 API입니다.")
    public ApiResponse<PostDTO.PostResponse.CreatePostResponse> createPost(
            @PathVariable Long userId,
            @RequestPart("createPostRequest") @Valid PostDTO.PostRequest.CreatePostRequest createPostRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        PostDTO.PostResponse.CreatePostResponse response = postCommandService.createPost(createPostRequest, files);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/update/{postId}/{userId}")
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<PostDTO.PostResponse.CreatePostResponse> updatePost(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestPart("updatePostRequest") @Valid PostDTO.PostRequest.UpdatePostRequest updatePostRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        PostDTO.PostResponse.CreatePostResponse response = postCommandService.updatePost(postId, updatePostRequest, files);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/delete/{postId}/{userId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 게시글을 삭제하는 API입니다.")
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
}
