package backend.like_house.domain.chatting.controller;

import backend.like_house.domain.chatting.dto.ChatDTO.ChatListResponse;
import backend.like_house.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatRooms")
@Tag(name = "채팅 관련 컨트 롤러, 소켓 엔드 포인트는 /chat")
public class ChatController {

    @Operation(summary = "채팅 불러오기 API", description = "채팅을 불러옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHAT4001", description = "더 이상 채팅이 존재하지 않습니다."),
    })
    @GetMapping("/{chatRoomId}/chats")
    public ApiResponse<ChatListResponse> getChat(
            @PathVariable Long chatRoomId,
            @RequestParam(name = "cursor") Integer cursor,
            @RequestParam(name = "take") Integer take,
            @RequestParam(name = "isUp") Boolean isUp
    ) {

        return ApiResponse.onSuccess(null);
    }


}
