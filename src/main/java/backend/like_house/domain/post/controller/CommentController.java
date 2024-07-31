package backend.like_house.domain.post.controller;

import backend.like_house.domain.post.dto.CommentDTO.CommentResponse.*;
import backend.like_house.domain.post.dto.CommentDTO.CommentRequest.*;
import backend.like_house.domain.post.service.CommentCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0/comments")
public class CommentController {

    private final CommentCommandService commentCommandService;

    @PostMapping("")
    @Operation(summary = "댓글 작성 API", description = "새로운 댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT4001", description = "댓글 작성 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    public ApiResponse<CreateCommentResponse> createComment(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody @Valid CreateCommentRequest createCommentRequest
    ) {
        CreateCommentResponse response = commentCommandService.createComment(user, createCommentRequest);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정 API", description = "특정 댓글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT4002", description = "댓글 수정 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "commentId", description = "댓글의 ID, path variable 입니다."),
    })
    public ApiResponse<CreateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody @Valid UpdateCommentRequest updateCommentRequest
    ) {
        CreateCommentResponse response = commentCommandService.updateComment(user, commentId, updateCommentRequest);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제 API", description = "특정 댓글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT4003", description = "댓글 삭제 실패."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "commentId", description = "댓글의 ID, path variable 입니다."),
    })
    public ApiResponse<String> deleteComment(
            @PathVariable Long commentId,
            @Parameter(hidden = true) @LoginUser User user
    ) {
        commentCommandService.deleteComment(user, commentId);
        return ApiResponse.onSuccess("댓글 삭제 성공");
    }

    @PatchMapping("/{commentId}/comment-alarm")
    @Operation(summary = "댓글 알림 끄기/켜기 API", description = "사용자가 특정 댓글 알림을 끄거나 켜는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "commentId", description = "댓글의 ID, path variable 입니다."),
            @Parameter(name = "enable", description = "댓글 알림 활성화 여부, query parameter 입니다.")
    })
    public ApiResponse<String> toggleCommentAlarm(
            @PathVariable Long commentId,
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam Boolean enable
    ) {
        commentCommandService.toggleCommentAlarm(user, commentId, enable);
        return ApiResponse.onSuccess("댓글 알림 끄기/켜기 성공");
    }
}
