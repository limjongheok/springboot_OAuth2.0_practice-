package io.securitys.basicsecurity.Config;

import org.springframework.boot.autoconfigure.graphql.security.GraphQlWebFluxSecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //인가 정책
        http.authorizeRequests()
                .anyRequest()
                .authenticated();




        // 인증 정책
        http.formLogin()
                //.loginPage("/loginPage") // 로그인 페이지 주소
                .defaultSuccessUrl("/") // 로그인 성공시 넘어갈 주소
                .failureUrl("/loginPatae") // 로그인 실패시 넘어갈 주소
                .usernameParameter("userId") // form username param 값
                .passwordParameter("passwd")// form  password param 값
                .loginProcessingUrl("/login_proc") // form action param 값
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("auth"+ authentication.getName());
                        response.sendRedirect("/");
                    }
                }) // 성공 시 handler - 응답으로 /보내짐
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception"+exception.getMessage());
                        response.sendRedirect("/loginPage");
                    }
                })// 실패시 handler -응답으로 /loginpage 이동
                .permitAll()// /loginPage 는 인증을 받지 않아도 가도됨
        ;


    }
}
