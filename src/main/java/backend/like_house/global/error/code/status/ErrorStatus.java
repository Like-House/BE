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

    // 유저 관려 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 가입된 유저 입니다."),
    ALREADY_BELONG_FAMILY_SPACE(HttpStatus.BAD_REQUEST, "USER4003", "이미 가족 공간에 소속되어 있습니다."),

    // 인증 관련 에러
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4001", "유효하지 않은 토큰입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH4002", "비밀번호가 일치하지 않습니다."),
    INVALID_ACCESS(HttpStatus.FORBIDDEN, "AUTH4003", "접근 권한이 없습니다."),

    // 일정 관련 에러
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE4001", "존재하지 않는 일정 입니다."),

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
