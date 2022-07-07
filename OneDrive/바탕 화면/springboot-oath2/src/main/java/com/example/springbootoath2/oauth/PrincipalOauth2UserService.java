package com.example.springbootoath2.oauth;

import com.example.springbootoath2.Model.User;
import com.example.springbootoath2.Repository.UserRepository;
import com.example.springbootoath2.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


// 후처리
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    // 구글로 부터 받는 userRequest 데이터에 대한 후처리 되는 함수

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("UserRequest: "+ userRequest);
        System.out.println(userRequest.getAccessToken());
        System.out.println("getClientRegistration" + userRequest.getClientRegistration()); // registrationid  어떤 oatuh로 로그인 햇는지 확인가능
        //구글 로그인 버튼 -> 구글 로그인창 -> 로그인을 완료  -> code를 리턴 (OAuth-Client라이브러리) -> Access Token  요처 ㅇ
        //userRequest 정보 -> (loadUser) -> 회원 프로필

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttribute"+ oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String username = provider + "_" + providerId;
        String password  = bCryptPasswordEncoder.encode("rptdlsepdj");
        String role ="ROLE_USER";
        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            userEntity = User.builder().username(username).password(password).email(email).role(role).provider(provider).providerId(providerId).build();
            userRepository.save(userEntity);


        }

        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
