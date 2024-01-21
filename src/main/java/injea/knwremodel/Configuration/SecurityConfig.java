package injea.knwremodel.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import injea.knwremodel.User.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService OAuthService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/**")
                        ).permitAll()
                )

                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                )

                .oauth2Login((oauth2) -> oauth2                                          // OAuth2기반의 로그인 할 경우 설정 함수, OAuth2LoginConfigurer를 불러옴.
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint           // 로그인 성공 후 사용자 정보를 가져온다.
                                .userService(OAuthService)                               // 사용자 정보를 처리할 서비스 지정.
                        )
                        .defaultSuccessUrl("/", true)        // 로그인 성공 시 이동할 URL
                );

        return http.build();
    }
    
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}