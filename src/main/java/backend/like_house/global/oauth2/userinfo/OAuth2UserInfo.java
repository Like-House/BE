package backend.like_house.global.oauth2.userinfo;


import java.time.LocalDate;

public interface OAuth2UserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();

    String getProfileImage();

    LocalDate getBirthDate();
}