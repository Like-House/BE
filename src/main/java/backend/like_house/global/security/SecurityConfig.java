package backend.like_house.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                //new AntPathRequestMatcher("/api/**"),
                                new AntPathRequestMatcher("/swagger-ui.html"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/api-docs/**"),
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/favicon.ico"),
                                new AntPathRequestMatcher("/health")
                        ).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
}
