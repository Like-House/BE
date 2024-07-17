package backend.like_house.domain.user.service;

import backend.like_house.domain.user.dto.UserDTO;

public interface UserCommandService {

    UserDTO.SignUpResponse signUp(UserDTO.SignUpRequest request);

    UserDTO.SignInResponse signIn(UserDTO.SignInRequest request);

}
