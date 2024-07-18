package backend.like_house.global.security.filter;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UserException e) { // UserException 잡아서 Unauthorized 상태 코드 설정
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, e);
        } catch (IOException e) { // IOException 잡아서 Internal Server Error 상태 코드 설정
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, new UserException(ErrorStatus._INTERNAL_SERVER_ERROR));
        } catch (Exception e) { // 그 외의 모든 Exception을 잡아 Internal Server Error 상태 코드 설정
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, new UserException(ErrorStatus._INTERNAL_SERVER_ERROR));
        }
    }

    private void setErrorResponse(HttpServletResponse response,
                                  HttpStatus status,
                                  UserException e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        ErrorStatus errorStatus = (ErrorStatus) e.getCode();
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                errorStatus.getCode(), errorStatus.getMessage(), e.getMessage());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
