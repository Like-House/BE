package backend.like_house.domain.post.controller;

import backend.like_house.domain.post.dto.CommentDTO;
import backend.like_house.domain.post.service.CommentCommandService;
import backend.like_house.domain.post.service.CommentQueryService;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0/comment")
public class CommentController {

    private final CommentQueryService commentQueryService;
    private final CommentCommandService commentCommandService;

    @PostMapping("/create/{userId}")
    @Operation(summary = "댓글 작성 API", description = "새로운 댓글을 작성하는 API입니다.")
    public ApiResponse<CommentDTO.CommentResponse.CreateCommentResponse> createComment(
            @PathVariable Long userId,
            @RequestBody @Valid CommentDTO.CommentRequest.CreateCommentRequest createCommentRequest
    ) {
        CommentDTO.CommentResponse.CreateCommentResponse response = commentCommandService.createComment(userId, createCommentRequest);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/update/{commentId}/{userId}")
    @Operation(summary = "댓글 수정 API", description = "특정 댓글을 수정하는 API입니다.")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<CommentDTO.CommentResponse.CreateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @PathVariable Long userId,
            @RequestBody @Valid CommentDTO.CommentRequest.UpdateCommentRequest updateCommentRequest
    ) {
        CommentDTO.CommentResponse.CreateCommentResponse response = commentCommandService.updateComment(userId, commentId, updateCommentRequest);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/delete/{commentId}/{userId}")
    @Operation(summary = "댓글 삭제 API", description = "특정 댓글을 삭제하는 API입니다.")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글의 ID, path variable 입니다."),
            @Parameter(name = "userId", description = "사용자의 ID, path variable 입니다.")
    })
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
        commentCommandService.deleteComment(commentId, userId);
        return ApiResponse.onSuccess(null);
    }
}
