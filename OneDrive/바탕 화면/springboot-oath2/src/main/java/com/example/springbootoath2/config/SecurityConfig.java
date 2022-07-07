package com.example.springbootoath2.config;

import com.example.springbootoath2.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 , prePost 활성화
// 1. 코드 받기(인증) ,2. access토큰(권한) 3. 사용자 프로필 정보를 가져와서 4. 정보를 토대로 회원 가입을 자동으로 진행 시키기
//4-2 그 정보가 좀 모자람 (추가적으로 받을 주소)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoderPwd()
    {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hseRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()

                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginForm")
                .defaultSuccessUrl("/")
                .and() // oath2.0
                .oauth2Login()
                .loginPage("/login") // 구글  로그인이 완료된 후 후처리 tip: 코드를 받는게 아니라 (엑세스 토큰+사용자 프로필 정보를 받음)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        ;
    }
}
