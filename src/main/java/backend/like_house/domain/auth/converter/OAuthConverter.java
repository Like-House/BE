package backend.like_house.domain.auth.converter;

import backend.like_house.domain.auth.dto.GoogleDTO;
import backend.like_house.domain.auth.dto.NaverDTO;
import backend.like_house.domain.user.entity.Role;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class OAuthConverter {

    public static User toKakaoUser(String nickname, String profileImg, String email) {
        return User.builder()
                .name(nickname)
                .profileImage(profileImg)
                .email(email)
                .socialType(SocialType.KAKAO)
                .role(Role.ROLE_USER)
                .build();
    }

    public static User toNaverUser(NaverDTO.NaverProfile request) {

        String birthyear = request.getResponse().getBirthyear(); // 2001
        String birthday = request.getResponse().getBirthday(); // 01-01

        String birthDateString = birthyear + "-" + birthday;

        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return User.builder()
                .name(request.getResponse().getName())
                .email(request.getResponse().getEmail())
                .profileImage(request.getResponse().getProfile_image())
                .birthDate(birthDate)
                .socialType(SocialType.NAVER)
                .role(Role.ROLE_USER)
                .build();
    }

    public static User toGoogleUser(GoogleDTO.GoogleProfile request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .profileImage(request.getPicture())
                .socialType(SocialType.GOOGLE)
                .role(Role.ROLE_USER)
                .build();
    }
}
