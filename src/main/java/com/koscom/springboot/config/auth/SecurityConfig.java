package com.koscom.springboot.config.auth;

import com.koscom.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // (1) security 기능 활성화 시킬 거야
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${security.enabled:true}")
    private boolean securityEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // (2)
                .and()
                    .authorizeRequests() // (3)
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() //인증 안해도 사용할 수 있게
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 최소 user role 가지고 있어야 함. = 인가
                        .anyRequest().authenticated() // (5) 나머지는 모두 인증이 되어야 한다. 권한이 있든 없든 인증만 되면 상관없어.
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // (6) 로그아웃이면 index.mustache 파일로.
                .and()
                    .oauth2Login() // (7)
                        .userInfoEndpoint() // (8)
                            .userService(customOAuth2UserService); //
    }

    //테스트에서 사용할 경우 security 무시
    @Override
    public void configure(WebSecurity web) throws Exception{
        if(!securityEnabled){
            web.ignoring().antMatchers("/**");
        }
    }
}
