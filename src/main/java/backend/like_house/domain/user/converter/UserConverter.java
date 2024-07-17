package backend.like_house.domain.user.converter;

import backend.like_house.domain.user.dto.UserDTO;
import backend.like_house.domain.user.entity.User;

public class UserConverter {

    public static UserDTO.SignUpResponse toSignUpResponseDTO(User user) {
        return UserDTO.SignUpResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserDTO.SignInResponse toSignInResponseDTO(String accessToken) {
        return UserDTO.SignInResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public static User toUser(UserDTO.SignUpRequest signUpRequest, String password) {
        return User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(password)
                .birthDate(signUpRequest.getBirthDate())
                .profileImage(signUpRequest.getProfileImage())
                .build();
    }
}
