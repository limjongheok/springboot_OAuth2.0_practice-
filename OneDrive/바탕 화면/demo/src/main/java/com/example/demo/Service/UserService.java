package com.example.demo.Service;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getEmail()==null){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        // 아이디 존재 유무 검사
        if(userRepository.existsByEmail(email)){
            log.warn("eamil alreay exists{}", email);
        }
        return userRepository.save(userEntity);
    }
    // email, pwd 검사
    public UserEntity getByCredentials(final  String email, final String password , final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByEmail(email);
        if(originalUser != null && encoder.matches(password,originalUser.getPassword())){
            return originalUser;
        }
        return null;
    }



}
