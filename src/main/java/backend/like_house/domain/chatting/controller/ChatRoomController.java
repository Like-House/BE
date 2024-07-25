package backend.like_house.domain.chatting.controller;

import backend.like_house.domain.chatting.converter.ChatRoomConverter;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomListResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomRequest;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ExitChatRoomRequest;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomRequest;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomResponse;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.service.ChatRoomCommandService;
import backend.like_house.domain.user.entity.User;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/chat-rooms")
@RequiredArgsConstructor
@Tag(name = "채팅방 관련 컨트 롤러")
public class ChatRoomController {

    private final ChatRoomCommandService chatRoomCommandService;

    @Operation(summary = "채팅방 불러 오기 API", description = "채팅방을 무한 스크롤로 불러옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHATROOM4001", description = "더 이상 채팅방이 존재하지 않습니다."),
    })
    @GetMapping("")
    public ApiResponse<ChatRoomListResponse> getChatRoom(
            @RequestParam(name = "cursor") Integer cursor,
            @RequestParam(name = "take") Integer take
    ) {
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "채팅방 생성하기 API", description = "채팅방을 생성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다."),
    })
    @PostMapping("")
    public ApiResponse<CreateChatRoomResponse> createChatRoom(
            @Parameter(hidden = true) @LoginUser User user, @RequestBody CreateChatRoomRequest createChatRoomRequest
    ) {
        ChatRoom chatRoom = chatRoomCommandService.createChatRoom(createChatRoomRequest, user);
        return ApiResponse.onSuccess(ChatRoomConverter.toCreateChatRoomResponse(chatRoom));
    }

    @Operation(summary = "채팅방 수정 하기 API", description = "채팅방 제목, 사진을 수정 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHATROOM4002", description = "해당 채팅방이 존재하지 않습니다."),
    })
    @PatchMapping("")
    public ApiResponse<UpdateChatRoomResponse> updateChatRoom(
            @RequestBody UpdateChatRoomRequest updateChatRoomRequest
    ) {
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "채팅방 나가기 API", description = "채팅방을 나갑니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHATROOM4002", description = "해당 채팅방이 존재하지 않습니다."),
    })
    @PostMapping("/exit")
    public ApiResponse<?> exitChatRoom(
            @RequestBody ExitChatRoomRequest exitChatRoomRequest
    ) {
        return ApiResponse.onSuccess(null);
    }
}
