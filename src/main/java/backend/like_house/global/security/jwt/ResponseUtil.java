package backend.like_house.global.security.jwt;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.error.code.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResponseUtil {

    private final ObjectMapper objectMapper;

    public void writeErrorResponse(HttpServletResponse response, ErrorStatus errorStatus, HttpStatus httpStatus) throws IOException {
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                errorStatus.getCode(),
                errorStatus.getMessage(),
                "");
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
