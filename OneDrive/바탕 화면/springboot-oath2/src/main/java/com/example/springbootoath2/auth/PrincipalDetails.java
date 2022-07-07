package com.example.springbootoath2.auth;


// 시큐리티가 /login 낚아채서 주소 요청이 오면 로그인 진행
// 로그인 진행이 완료가 될시 시큐리티 session을 만들어 줌
// 오브젝트 타입 => authentication 타입 객체
//authentication 안에 user정보가 있어야햄
//authentication 안에 user정보가 있어야함
// user 오브젝트 타입 => userDetails 타입

import com.example.springbootoath2.Model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

// login 주소 요청시 낚아챔
// 인증 객체 authenticaition 에 들어감

@Data
public class PrincipalDetails implements UserDetails , OAuth2User {

    private  Map<String, Object> attributes; //

    // OAth2User
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }



    //UserDetails
    private User user;

    // 일반 로그인
    public PrincipalDetails(User user){
        this.user = user;
    }

    // oauth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 user 권한 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 휴먼계정 설정 하기
        return true;
    }
}
