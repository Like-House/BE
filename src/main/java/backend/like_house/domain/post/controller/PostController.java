package backend.like_house.domain.post.controller;

import backend.like_house.domain.account.entity.Custom;
import backend.like_house.domain.post.converter.PostConverter;
import backend.like_house.domain.post.dto.PostDTO;
import backend.like_house.domain.post.entity.Post;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.s3.S3Manager;
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

    private final S3Manager s3Manager;
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
        // TODO: 가족 공간 ID로 게시글을 가져오는 로직
        List<Post> posts = null; // postService.getPosts(familySpaceId, cursor, take);
        // (글 쓴 사람) post에 있는 userId == profileId이고 (사용자) userId == contact의 user_id인 nickname
        List<String> authorNicknames = null; // postService.getAuthorNicknames(posts, userId, familySpaceId);
        List<Integer> likeCounts = null; // postService.getLikeCounts(posts);
        List<Integer> commentCounts = null; // postService.getCommentCounts(posts);
        List<List<String>> imageUrlsList = null; // postService.getPostImageUrls(posts);
        return ApiResponse.onSuccess(PostConverter.toGetPostListResponse(posts, authorNicknames, likeCounts, commentCounts, imageUrlsList));
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
        // TODO: postId를 사용하여 특정 게시글의 상세 정보를 조회하는 로직
        // 1. postId를 기반으로 게시글 조회
        // 2. 조회한 게시글의 상세 정보 반환
        Post post = null; // postService.getPostById(postId);
        String authorNickname = null; // postService.getAuthorNicknames(posts, userId, familySpaceId);
        int likeCount = 0; // postService.getLikeCount(post);
        int commentCount = 0; // postService.getCommentCount(post);
        List<String> imageUrls = null; // postService.getPostImageUrls(postId);
        List<PostDTO.PostResponse.FamilyTagResponse> taggedUsers = null; // postService.getFamilyTagResponses(postId, userId);
        return ApiResponse.onSuccess(PostConverter.toGetPostDetailResponse(post, authorNickname, likeCount, commentCount, imageUrls, taggedUsers));
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
        // TODO: familySpaceId를 기반으로 가족 태그를 조회하는 로직
        // 1. familySpaceId와 userId를 기반으로 해당 가족 공간의 멤버들 조회
        // 2. 각 멤버에 대해 사용자가 설정한 별명으로 반환
        List<User> users = null; // 가족 공간 멤버 조회
        List<Custom> customs = null; // 특정 사용자가 해당 가족 공간의 멤버들에게 설정한 별명
        return ApiResponse.onSuccess(PostConverter.toGetFamilyTagResponse(users, customs));
    }

    @PostMapping("/create/{userId}")
    @Operation(summary = "게시글 작성 API", description = "새로운 게시글을 작성하는 API입니다.")
    public ApiResponse<PostDTO.PostResponse.CreatePostResponse> createPost(
            @PathVariable Long userId,
            @RequestPart("createPostRequest") @Valid PostDTO.PostRequest.CreatePostRequest createPostRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        // TODO: 새로운 게시글을 작성하는 로직
        // 1. 파일을 S3에 업로드하고 파일 URL 목록 반환
        // 2. createPostRequest 객체를 사용하여 게시글 생성
        // 2. 생성한 게시글을 저장하고 저장된 게시글의 상세 정보 반환
        List<String> imageUrls = s3Manager.uploadFiles(files);
        Post post = null; // postService.createPost(createPostRequest);
        return ApiResponse.onSuccess(PostConverter.toCreatePostResponse(post));
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
        // TODO: 특정 게시글을 수정하는 로직
        // 1. 파일을 S3에 업로드하고 파일 URL 목록 반환
        // 2. updatePostRequest 데이터로 게시글 수정
        // 3. 수정된 게시글을 저장하고 저장된 게시글의 상세 정보 반환
        List<String> imageUrls = s3Manager.uploadFiles(files);
        Post post = null;
        return ApiResponse.onSuccess(PostConverter.toCreatePostResponse(post));
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
        // TODO: 특정 게시글을 삭제하는 로직
        // 1. postId를 기반으로 게시글 조회
        // 2. 조회한 게시글 삭제
        return ApiResponse.onSuccess(null);
    }
}
