package backend.like_house.global.error.code.status;

import backend.like_house.global.error.code.BaseErrorCode;
import backend.like_house.global.error.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 가족 공간 관련 에러
    ALREADY_EXIST_FAMILY_SPACE(HttpStatus.BAD_REQUEST, "FAMILY_SPACE4001", "이미 존재하는 가족 공간 입니다."),
    FAMILY_SPACE_NOT_FOUND(HttpStatus.BAD_REQUEST, "FAMILY_SPACE4002", "존재하지 않는 가족 공간 입니다."),
    NOT_INCLUDE_USER_FAMILY_SPACE(HttpStatus.BAD_REQUEST, "FAMILY_SPACE4003", "유저가 해당 가족 공간에 속해 있지 않습니다."),
    ALREADY_BELONG_USER_FAMILY_SPACE(HttpStatus.BAD_REQUEST, "FAMILY_SPACE4004", "이미 가족 공간에 소속되어 있습니다."),
    FAMILY_SPACE_CODE_EXPIRATION_INVALID(HttpStatus.BAD_REQUEST, "FAMILY_SPACE4005", "초대 코드가 유효하지 않습니다."),
    ALREADY_BELONG_OTHER_FAMILY_SPACE(HttpStatus.BAD_REQUEST, "FAMILY_SPACE4006", "이미 다른 가족 공간에 속해 있습니다."),

    // 유저 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 가입된 유저 입니다."),
    USER_NOT_MANAGER(HttpStatus.BAD_REQUEST, "USER4004", "주최자가 아닙니다."),
    ALREADY_REMOVED_USER(HttpStatus.BAD_REQUEST, "USER4005", "이미 해제된 유저입니다."),
    ALREADY_BLOCKED_USER(HttpStatus.BAD_REQUEST, "USER4006", "이미 차단된 유저입니다."),
    ALREADY_RELEASE_REMOVE_USER(HttpStatus.BAD_REQUEST, "USER4007", "이미 해제가 풀어진 유저입니다."),
    ALREADY_RELEASE_BLOCK_USER(HttpStatus.BAD_REQUEST, "USER4008", "이미 차단이 풀어진 유저입니다."),
    PASSWORD_SAME_AS_OLD(HttpStatus.BAD_REQUEST, "USER4009", "기존 비밀번호와 동일합니다."),

    // 인증 관련 에러
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4001", "유효하지 않은 토큰입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH4002", "비밀번호가 일치하지 않습니다."),
    INVALID_ACCESS(HttpStatus.FORBIDDEN, "AUTH4003", "접근 권한이 없습니다."),
    TOKEN_IN_BLACKLIST(HttpStatus.BAD_REQUEST, "AUTH4004", "로그아웃 or 탈퇴 처리된 토큰입니다."),
    LOGOUT_USER_TOKEN(HttpStatus.BAD_REQUEST, "AUTH4005", "로그아웃 처리된 토큰입니다."),
    DELETE_USER_TOKEN(HttpStatus.BAD_REQUEST, "AUTH4006", "탈퇴 처리된 토큰입니다."),

    // 일정 관련 에러
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE4001", "존재하지 않는 일정 입니다."),

    // 가족 이모티콘 관련 에러
    FAMILY_EMOTICON_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY_EMOTICON4001", "존재하지 않는 가족 이모티콘 입니다."),

    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4001", "존재하지 않는 게시글입니다."),
    POST_CREATE_FAILED(HttpStatus.BAD_REQUEST, "POST4002", "게시글 작성 실패."),
    POST_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "POST4003", "게시글 수정 실패."),
    POST_DELETE_FAILED(HttpStatus.BAD_REQUEST, "POST4004", "게시글 삭제 실패."),

    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "POSTLIKE4001", "이미 좋아요가 눌린 게시글입니다."),
    NOT_LIKED(HttpStatus.BAD_REQUEST, "POSTLIKE4002", "좋아요가 눌리지 않은 게시글입니다."),

    // 댓글 관련 에러
    COMMENT_CREATE_FAILED(HttpStatus.BAD_REQUEST, "COMMENT4001", "댓글 작성 실패."),
    COMMENT_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "COMMENT4002", "댓글 수정 실패."),
    COMMENT_DELETE_FAILED(HttpStatus.BAD_REQUEST, "COMMENT4003", "댓글 삭제 실패."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT4004", "존재하지 않는 댓글입니다."),
    COMMENT_INVALID_ACCESS(HttpStatus.FORBIDDEN, "COMMENT4005", "댓글에 대한 접근 권한이 없습니다."),

    // 파일 변환 에러
    FILE_CONVERSION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5001", "파일 변환 중 오류가 발생했습니다."),

    // 채팅 관련 에러
    INVALID_CHAT_SCROLL(HttpStatus.BAD_REQUEST, "CHAT4001", "더 이상 채팅이 존재하지 않습니다."),
    CHAT_NOT_SEND(HttpStatus.BAD_REQUEST, "CHAT4002", "전송 중 오류가 발생하였습니다."),
    INVALID_CHAT_FORMAT(HttpStatus.BAD_REQUEST, "CHAT4003", "채팅 형식이 유효하지 않습니다."),

    // 채팅방 관련 에러
    INVALID_CHATROOM_SCROLL(HttpStatus.BAD_REQUEST, "CHATROOM4001", "더 이상 채팅방이 존재하지 않습니다."),
    CHATROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "CHATROOM4002", "해당 채팅방이 존재하지 않습니다."),
    ALREADY_JOIN_CHATROOM(HttpStatus.BAD_REQUEST, "CHATROOM4003", "이미 채팅방에 들어와있습니다."),
    FIRST_JOIN_CHATROOM(HttpStatus.BAD_REQUEST, "CHATROOM4004", "채팅방에 먼저 들어와 있어야 합니다."),
    NOT_JOIN_CHATROOM(HttpStatus.BAD_REQUEST, "CHATROOM4005", "채팅방에 가입되있지 않습니다."),
    FAILED_ENTER_CHATROOM(HttpStatus.INTERNAL_SERVER_ERROR, "CHATROOM5001", "채팅 방 들어가기에 실패하였습니다"),
    FAILED_EXIT_CHATROOM(HttpStatus.INTERNAL_SERVER_ERROR, "CHATROOM5002", "채팅 방 나가기에 실패하였습니다"),
    INVALID_CHATROOM(HttpStatus.INTERNAL_SERVER_ERROR, "CHATROOM5003", "정상적인 채팅방이 아닙니다. 새로운 채팅방을 다시 생성해주세요"),
    OVERLAP_JOIN_USER(HttpStatus.BAD_REQUEST, "CHATROOM4006", "들어오는 user와 participant의 user의 id가 같으면 안됩니다."),

    // 가족 앨범 관련 에러
    FAMILY_ALBUM_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY_ALBUM4001", "존재하지 않는 가족 앨범 입니다."),

    // 페이징 관련 에러
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "PAGE4001", "올바르지 않은 페이징 번호입니다."),

    // 사이즈 관련 에러
    INVALID_SIZE_NUMBER(HttpStatus.BAD_REQUEST, "SIZE4001", "올바르지 않은 사이즈입니다."),

    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
