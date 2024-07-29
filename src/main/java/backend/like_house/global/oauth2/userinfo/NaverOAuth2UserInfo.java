package backend.like_house.global.oauth2.userinfo;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@AllArgsConstructor
public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("name");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("profile_image");

    }

    public LocalDate getBirthDate() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String birthyear = (String) response.get("birthyear"); // 2001
        String birthday = (String) response.get("birthday");// 01-01

        String birthDateString = birthyear + "-" + birthday;

        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return birthDate;
    }
}