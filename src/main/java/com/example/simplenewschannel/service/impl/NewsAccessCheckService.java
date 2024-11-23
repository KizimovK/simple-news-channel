package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.repository.NewsRepository;
import com.example.simplenewschannel.service.AbstractAccessCheckService;
import com.example.simplenewschannel.service.AccessCheckService;
import com.example.simplenewschannel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewsAccessCheckService extends AbstractAccessCheckService {

    private final NewsRepository newsRepository;

    @Override
    public AccessType getType() {
        return AccessType.NEWS;
    }

    @Override
    protected boolean check(long id, long idUser) {
        return newsRepository.existsByIdAndAuthorId(id, idUser);
    }
}
