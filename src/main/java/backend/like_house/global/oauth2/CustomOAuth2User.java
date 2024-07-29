package backend.like_house.global.oauth2;

import backend.like_house.domain.user.entity.Role;
import backend.like_house.domain.user.entity.SocialType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private String email;
    private Role role;
    private SocialType socialType;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String email, Role role, SocialType socialType) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
        this.socialType = socialType;
    }
}
