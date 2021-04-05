package com.chaejm55.book.springboot.config.auth;

import com.chaejm55.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;


@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리트 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserTypesOAuth2UserService customUserTypesOAuth2UserService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()  // h2-console 사용을 위한 옵션 제거
                .and()
                    .authorizeRequests() // URL 별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2/console/**").permitAll() // 모두에게 허용
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권한 관리 대상을 지정함 user만
                    .anyRequest().authenticated() // 나머지 URL, 인증된 사용자에게만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 홈페이지로 이동
                .and()
                    .oauth2Login() // 설정 진입점
                        .userInfoEndpoint() // 로그인 성공 후 사용자 정보를 가져올때의 설정
                            .userService(customUserTypesOAuth2UserService); // 로그인 성공후 후속 조치를 실행할 구현체
    }
}
