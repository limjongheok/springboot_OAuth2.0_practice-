package com.example.demo.security;

import com.example.demo.Entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

// JWT 생성
@Slf4j
@Service
public class TokenProvider {
    private static  final String SECRET_KEY = "NMA8JPctFuna59f5";

    public String create(UserEntity userEntity){
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        /*
        {// header
            "alg":"HS512"
        },
        {//PAYLOAD
            "sub":"40288093784915d201784916a40c0001",
            "iss":"demo app"
            "iat":1595733657,
            "exp": 1596597657
        },
        //Sercet_key
        Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWKRv50_17bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg

         */

        //JWT 토큰 생성
        return Jwts.builder()
                // HEADER 에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                //payload 들어갈 내용
                .setSubject(userEntity.getId()) //sub
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        // parseClaimsJws 메서드가 base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey 로 넘오온 시크릿을 이요해 서명후 toekn 의 서명과 비교
        // 위조 되지 않았다면 페이로드 Claims 리턴 위조라면 예외
        // 그중 우리는 userId 필요하므로 getBody를 부름

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
