package backend.like_house.domain.auth.service;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.user.entity.User;

public interface AuthCommandService {

    AuthDTO.SignUpResponse signUp(AuthDTO.SignUpRequest request);

    AuthDTO.SignInResponse signIn(AuthDTO.SignInRequest request);

    void signOut(AuthDTO.TokenRequest request);

    void deleteUser(AuthDTO.TokenRequest request);

    void fcmSave(User user, AuthDTO.FcmRequest tokenRequest);
}
