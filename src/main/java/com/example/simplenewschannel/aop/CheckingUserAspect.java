package com.example.simplenewschannel.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
//Todo: доделать AOP
@Aspect
@Component
@Slf4j
public class CheckingUserAspect {
    @Before("@annotation(CheckingUser)")
    public void checkingUserBefore(JoinPoint joinPoint){
        log.info("Check User");
    }
}
