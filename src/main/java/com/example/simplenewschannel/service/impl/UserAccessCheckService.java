package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.repository.UserRepository;
import com.example.simplenewschannel.service.AccessCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
public class UserAccessCheckService implements AccessCheckService {
    private final UserRepository userRepository;

    public UserAccessCheckService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean getResultCheck(HttpServletRequest request, Object[] arguments) {
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long id = Long.parseLong(pathVariables.get("id"));
        return userRepository.existsById(id);
    }

    @Override
    public AccessType getType() {
        return AccessType.USER;
    }
}
