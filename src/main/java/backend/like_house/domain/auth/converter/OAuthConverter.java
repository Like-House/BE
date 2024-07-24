package backend.like_house.domain.auth.converter;

import backend.like_house.domain.auth.dto.NaverDTO;
import backend.like_house.domain.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class OAuthConverter {

    public static User toKakaoUser(String nickname, String profileImg, String email) {
        return User.builder()
                .name(nickname)
                .profileImage(profileImg)
                .email(email)
                .socialName("KAKAO")
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
                .socialName("NAVER")
                .build();
    }
}
