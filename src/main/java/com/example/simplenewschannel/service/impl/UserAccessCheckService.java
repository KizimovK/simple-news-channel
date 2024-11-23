package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.repository.UserRepository;
import com.example.simplenewschannel.service.AbstractAccessCheckService;
import com.example.simplenewschannel.service.AccessCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
public class UserAccessCheckService extends AbstractAccessCheckService {

    @Override
    public AccessType getType() {
        return AccessType.USER;
    }

    @Override
    protected boolean check(long id, long idUser) {
        return id == idUser;
    }
}
