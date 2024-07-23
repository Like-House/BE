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


    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4001", "존재하지 않는 게시글입니다."),
    POST_CREATE_FAILED(HttpStatus.BAD_REQUEST, "POST4002", "게시글 작성 실패."),
    POST_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "POST4003", "게시글 수정 실패."),
    POST_DELETE_FAILED(HttpStatus.BAD_REQUEST, "POST4004", "게시글 삭제 실패."),

    // 댓글 관련 에러
    COMMENT_CREATE_FAILED(HttpStatus.BAD_REQUEST, "COMMENT4001", "댓글 작성 실패."),
    COMMENT_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "COMMENT4002", "댓글 수정 실패."),
    COMMENT_DELETE_FAILED(HttpStatus.BAD_REQUEST, "COMMENT4003", "댓글 삭제 실패."),

    // 멤버 관려 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

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
