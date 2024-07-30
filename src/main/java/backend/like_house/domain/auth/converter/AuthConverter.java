package backend.like_house.domain.auth.converter;

import backend.like_house.domain.auth.dto.AuthDTO;
import backend.like_house.domain.user.entity.Role;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;

public class AuthConverter {

    public static AuthDTO.SignUpResponse toSignUpResponseDTO(User user) {
        return AuthDTO.SignUpResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }

    public static AuthDTO.SignInResponse toSignInResponseDTO(String accessToken, String refreshToken) {
        return AuthDTO.SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static User toUser(AuthDTO.SignUpRequest signUpRequest, String password, String profileImg) {
        return User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(password)
                .birthDate(signUpRequest.getBirthDate())
                .profileImage(profileImg)
                .role(Role.ROLE_USER)
                .socialType(SocialType.LOCAL)
                .build();
    }
}
