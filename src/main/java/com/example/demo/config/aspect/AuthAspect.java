package com.example.demo.config.aspect;


import com.example.demo.config.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.http.HttpHeaders;

@Aspect
@RequiredArgsConstructor
@Component
public class AuthAspect {
    private final AuthService authService;
    @Before("@annotation(TokenRequired)")
    public void checkToken(){
        ServletRequestAttributes request =
                (ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes();
        String token = request.getRequest().getHeader("Authorization");
        if(token== null) throw new RuntimeException("token is null");
        token = token.replace("Bearer ","");
        System.out.println(authService.getClaims(token));
    }

}
