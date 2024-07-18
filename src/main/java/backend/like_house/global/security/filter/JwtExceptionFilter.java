package backend.like_house.global.security.filter;

import backend.like_house.global.common.ApiResponse;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;
import backend.like_house.global.error.handler.AuthException;
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
        } catch (AuthException e) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response,
                                  HttpStatus status,
                                  GeneralException e)  {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        ErrorStatus errorStatus = (ErrorStatus) e.getCode();
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                errorStatus.getCode(), errorStatus.getMessage(), e.getMessage());

        final ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(response.getOutputStream(), errorResponse);
        } catch (IOException ioException) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }
    }
}
