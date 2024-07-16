package backend.like_house.domain.user.service;

import backend.like_house.domain.user.dto.SignInDTO;
import backend.like_house.domain.user.dto.SignUpDTO;

public interface UserCommandService {

    SignUpDTO.SignUpResponse signUp(SignUpDTO.SignUpRequest request);

    SignInDTO.SignInResponse signIn(SignInDTO.SignInRequest request);

}
