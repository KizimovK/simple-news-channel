package com.example.simplenewschannel.service;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.aop.Accessible;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessCheckService {
    boolean getResultCheck(HttpServletRequest request, Accessible accessible);

    AccessType getType();
}
