package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.AuthDTO;

public interface AuthCommandService {

    AuthDTO.SignUpResponse signUp(AuthDTO.SignUpRequest request);

    AuthDTO.SignInResponse signIn(AuthDTO.SignInRequest request);

    void signOut(AuthDTO.TokenRequest request);


}
