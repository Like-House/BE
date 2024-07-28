package backend.like_house.domain.chatting.controller;

import backend.like_house.domain.chatting.dto.ChatDTO;
import backend.like_house.domain.chatting.dto.ChatDTO.ChatListResponse;
import backend.like_house.domain.chatting.service.ChatQueryService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/chat-rooms")
@Tag(name = "채팅 관련 컨트 롤러, 소켓 엔드 포인트는 /chat")
public class ChatController {

    private final ChatQueryService chatQueryService;

    @Operation(summary = "채팅 불러오기 API",
            description = "cursor 값이 -1일 시 최초 값을 불러옵니다. 이때는 take 값에 아무 것이나 넣어도 됩니다. (마지막 채팅방을 들어온 시각으로부터 그 후 내용이 전부 나옴.)\n " +
                    "그 이후로는 nextCursor와 take에 값을 넣고 조회를 해주 시면 됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHAT4001", description = "더 이상 채팅이 존재하지 않습니다."),
    })
    @GetMapping("/{chatRoomId}/chats")
    public ApiResponse<ChatListResponse> getChats(
            @Parameter(hidden = true) @LoginUser User user,
            @PathVariable Long chatRoomId,
            @RequestParam(name = "cursor") Long cursor,
            @RequestParam(name = "take") Integer take
    ) {
        ChatDTO.ChatListResponse chats = chatQueryService.getChats(user, chatRoomId, cursor, take);
        return ApiResponse.onSuccess(chats);
    }


}
