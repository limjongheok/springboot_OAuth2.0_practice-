package com.example.springbootoath2.auth;

import com.example.springbootoath2.Model.User;
import com.example.springbootoath2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



// 시큐리티 설정에서 loginProcessingUrl("/login");
//login 요칭이 오면 자동으로 UserDetailsService 타입으로 ioc되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    // 시큐리티 session = authentication = userDetails;
    // 즉 이것은 security section 내부이다 .
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            System.out.println(userEntity.getPassword());
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
