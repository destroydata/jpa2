package com.example.demo.config.auth;


import com.example.demo.members.domain.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class AuthService {
    public String makeToken(Member member){
        String secretKey = "secret-s--ssss-s-p-d--c-b--g--f--dsakey";
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), hs256.getJcaName());
        String compact = Jwts.builder()
                .claim("memberId", member.getId())
                .claim("name", member.getName())
                .claim("age", member.getAge())
                .setExpiration(new Date(System.currentTimeMillis() + 120_000))
                .signWith(key)
                .compact();
        return compact;
    }
}
