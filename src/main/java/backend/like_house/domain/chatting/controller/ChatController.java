package backend.like_house.domain.chatting.controller;

import backend.like_house.domain.chatting.dto.ChatDTO;
import backend.like_house.domain.chatting.dto.ChatDTO.ChatListResponse;
import backend.like_house.domain.chatting.service.ChatQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import backend.like_house.global.validation.annotation.CheckCursor;
import backend.like_house.global.validation.annotation.CheckPage;
import backend.like_house.global.validation.annotation.CheckSize;
import backend.like_house.global.validation.annotation.ExistChatRoom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v0/chat-rooms")
@Tag(name = "채팅 관련 컨트 롤러, 소켓 엔드 포인트는 /chat")
public class ChatController {

    private final ChatQueryService chatQueryService;

    @Operation(summary = "채팅 무한 스크롤 불러 오기 API",
            description = "첫 ENTER 이후로 사용하셔야 합니다. nextCursor와 take에 값을 넣고 조회를 해주 시면 됩니다. hasNext가 false고 nextCursor가 null이라면 마지막 페이지입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHATROOM4002", description = "해당 채팅방이 존재하지 않습니다.."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SIZE4001", description = "올바르지 않은 사이즈입니다.")
    })
    @GetMapping("/{chatRoomId}/chats")
    public ApiResponse<ChatListResponse> getChats(
            @Parameter(hidden = true) @LoginUser User user,
            @PathVariable @ExistChatRoom Long chatRoomId,
            @Parameter(description = "처음 요청은 1로 해주세요.") @RequestParam(name = "cursor") @CheckCursor Long cursor,
            @Parameter(description = "처음 요청 시 take 값은 아무 값이나 상관 없습니다.") @RequestParam(name = "take") @CheckSize Integer take
    ) {
        ChatDTO.ChatListResponse chats = chatQueryService.getChats(user, chatRoomId, cursor, take);
        return ApiResponse.onSuccess(chats);
    }



}
