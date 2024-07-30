package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.AuthDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AuthCommandService {

    AuthDTO.SignUpResponse signUp(AuthDTO.SignUpRequest request, MultipartFile profileImg);

    AuthDTO.SignInResponse signIn(AuthDTO.SignInRequest request);

}
