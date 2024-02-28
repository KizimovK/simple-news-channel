package com.example.simplenewschannel.service;

import com.example.simplenewschannel.aop.AccessType;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessCheckService {
    boolean getResultCheck(HttpServletRequest request, Object[] arguments);

    AccessType getType();
}
