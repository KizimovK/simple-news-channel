package com.example.simplenewschannel.aop;

import com.example.simplenewschannel.exception.AccessiblyCheckException;
import com.example.simplenewschannel.service.AccessCheckService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.MessageFormat;
import java.util.Map;


//Todo: доделать AOP
@Aspect
@Component
@Slf4j
public class AccessibleAspect {
    private final Map<AccessType,AccessCheckService> accessCheckServiceMap;

    public AccessibleAspect(Map<AccessType, AccessCheckService> accessCheckServiceMap) {
        this.accessCheckServiceMap = accessCheckServiceMap;
    }

    @Before("@annotation(accessible)")
    public void accessBefore(JoinPoint joinPoint, Accessible accessible) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalArgumentException("RequestAttributes not present!");
        }
        HttpServletRequest httpRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        AccessCheckService accessCheckService = accessCheckServiceMap.get(accessible.checkBy());
        Object[] arguments = joinPoint.getArgs();
        if (accessCheckService == null){
            throw new IllegalArgumentException("AccessCheckService this type not found");
        }
        if (!accessCheckService.getResultCheck(httpRequest, arguments)){
            throw new AccessiblyCheckException("the action is not available to the user");
        }
       log.info("Check ok!!!");
    }
}
