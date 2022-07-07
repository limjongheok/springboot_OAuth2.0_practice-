package com.example.springbootoath2.Controller;

import com.example.springbootoath2.Model.User;
import com.example.springbootoath2.Repository.UserRepository;
import com.example.springbootoath2.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.Role;
import java.util.Iterator;

@Controller
public class IndexController
{
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userD){ // 의존성 주입
        System.out.println("text/login");
        PrincipalDetails principalDetails =(PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails.getUser());
        System.out.println(userD.getUser());
        return "세션 정보 확인하기 ";

    }

    @GetMapping("/test/oauth//login")
    public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oath2){ // 의존성 주입
        System.out.println("text/login");
        OAuth2User oauth2 =(OAuth2User) authentication.getPrincipal();
        System.out.println("oauth2User" + oauth2.getAttributes());
        System.out.println(oath2.getAttributes());

        return "세션 정보 확인하기 ";

    }
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
        System.out.println("Principal : " + principal);
        System.out.println("OAuth2 : "+principal.getUser().getProvider());
        // iterator 순차 출력 해보기
        Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
        while (iter.hasNext()) {
            GrantedAuthority auth = iter.next();
            System.out.println(auth.getAuthority());
        }

        return "유저 페이지입니다.";
    }


    @GetMapping({"","/"})
    public String index(){
        return "index";
    }


    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }


    //스프링 시큐리티가 해당 주소를 낮아챔
    @GetMapping("/login")
    public  String loginForm(){
        return "login";
    }// 로그인 폼으로


    @GetMapping("/joinForm")
    public  String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println("회원가입 진행 : " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/login";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인 정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터";
    }


}
