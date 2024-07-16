package backend.like_house.domain.user.converter;

import backend.like_house.domain.user.dto.SignInDTO;
import backend.like_house.domain.user.dto.SignUpDTO;
import backend.like_house.domain.user.entity.User;

public class UserConverter {

    public static SignUpDTO.SignUpResponse toSignUpResponseDTO(User user) {
        return SignUpDTO.SignUpResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static SignInDTO.SignInResponse toSignInResponseDTO(String accessToken) {
        return SignInDTO.SignInResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public static User toUser(SignUpDTO.SignUpRequest signUpRequest, String password) {
        return User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(password)
                .birthDate(signUpRequest.getBirthDate())
                .profileImage(signUpRequest.getProfileImage())
                .build();
    }
}
