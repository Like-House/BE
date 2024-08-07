package backend.like_house.domain.chatting.controller;

import backend.like_house.domain.chatting.converter.ChatRoomConverter;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ChatRoomResponseList;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomRequest;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.CreateChatRoomResponse;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.ExitChatRoomRequest;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomRequest;
import backend.like_house.domain.chatting.dto.ChatRoomDTO.UpdateChatRoomResponse;
import backend.like_house.domain.chatting.entity.ChatRoom;
import backend.like_house.domain.chatting.service.ChatRoomCommandService;
import backend.like_house.domain.chatting.service.ChatRoomQueryService;
import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.user.service.UserQueryService;
import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.security.annotation.LoginUser;
import backend.like_house.global.validation.annotation.CheckCursor;
import backend.like_house.global.validation.annotation.CheckSize;
import backend.like_house.global.validation.annotation.ExistFamilySpace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/chat-rooms")
@RequiredArgsConstructor
@Validated
@Tag(name = "채팅방 관련 컨트 롤러")
public class ChatRoomController {

    private final ChatRoomCommandService chatRoomCommandService;
    private final ChatRoomQueryService chatRoomQueryService;
    private final UserQueryService userQueryService;

    @Operation(summary = "채팅방 불러 오기 API", description = "채팅방을 무한 스크롤로 불러옵니다. hasNext가 false고 nextCursor가 null이라면 마지막 페이지입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAMILY_SPACE4002", description = "존재하지 않는 가족 공간 입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SIZE4001", description = "올바르지 않은 사이즈입니다.")
    })
    @GetMapping("")
    public ApiResponse<ChatRoomResponseList> getChatRooms(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(name = "familySpaceId") @ExistFamilySpace Long familySpaceId,
            @Parameter(description = "처음 요청은 1로 해주세요.") @CheckCursor @RequestParam(name = "cursor") Long cursor,
            @RequestParam(name = "take") @CheckSize Integer take
    ) {
       ChatRoomResponseList chatRoomResponseList = chatRoomQueryService.getChatRoomsByUserIdAndFamilySpaceId(user.getId(), familySpaceId, cursor, take);
        return ApiResponse.onSuccess(chatRoomResponseList);
    }

    @Operation(summary = "채팅방 생성하기 API", description = "채팅방을 생성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "사용자를 찾을 수 없습니다."),
    })
    @PostMapping("")
    public ApiResponse<CreateChatRoomResponse> createChatRoom(
            @Parameter(hidden = true) @LoginUser User user, @RequestBody @Valid CreateChatRoomRequest createChatRoomRequest
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
            @RequestBody @Valid UpdateChatRoomRequest updateChatRoomRequest
    ) {
        ChatRoom chatRoom = chatRoomCommandService.updateChatRoom(updateChatRoomRequest);
        return ApiResponse.onSuccess(ChatRoomConverter.toUpdateChatRoomResponse(chatRoom));
    }

    @Operation(summary = "채팅방 나가기 API", description = "채팅방을 나갑니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHATROOM4002", description = "해당 채팅방이 존재하지 않습니다."),
    })
    @PostMapping("/exit")
    public ApiResponse<?> exitChatRoom(
            @Parameter(hidden = true) @LoginUser User user, @RequestBody ExitChatRoomRequest exitChatRoomRequest
    ) {
        chatRoomCommandService.exitChatRoom(exitChatRoomRequest, user);
        return ApiResponse.onSuccess("해당 chatroom을 나갔습니다.");
    }

    @GetMapping("/{chatRoomId}/users")
    @Operation(summary = "채팅 방에 속해 있는 유저 불러 오기 API", description = "채팅 방에 속해 있는 유저 불러 올 수 있습 니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHATROOM4002", description = "해당 채팅방이 존재하지 않습니다.")
    })
    public ApiResponse<UserDTO.ChatRoomUserListResponse> getUserByChatRoom(@PathVariable Long chatRoomId) {
        UserDTO.ChatRoomUserListResponse chatRoomUserListResponse = userQueryService.getUserByChatRoom(chatRoomId);
        return ApiResponse.onSuccess(chatRoomUserListResponse);
    }
}
