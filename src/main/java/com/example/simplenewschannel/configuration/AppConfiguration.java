package com.example.simplenewschannel.configuration;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.service.AccessCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AppConfiguration {
    @Bean
    public Map<AccessType, AccessCheckService> accessCheckServices(Collection<AccessCheckService> checkServices){
        return checkServices.stream().collect(Collectors.toMap(AccessCheckService::getType, Function.identity()));
    }
}
