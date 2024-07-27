package backend.like_house.global.security.principal;

import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.domain.user.entity.User;
import backend.like_house.domain.auth.repository.AuthRepository;
import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.exception.GeneralException;
import backend.like_house.global.error.handler.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] parts = username.split(":", 2);
        if (parts.length != 2) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }

        String email = parts[0];
        String socialNameStr = parts[1];
        SocialType socialType;

        try {
            socialType = SocialType.valueOf(socialNameStr);
        } catch (IllegalArgumentException e) {
            throw new AuthException(ErrorStatus._BAD_REQUEST);
        }

        User user = authRepository.findByEmailAndSocialType(email, socialType)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}