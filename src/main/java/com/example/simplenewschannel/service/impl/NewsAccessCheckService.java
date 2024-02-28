package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.dto.request.UpsertNewsRequest;
import com.example.simplenewschannel.repository.NewsRepository;
import com.example.simplenewschannel.service.AccessCheckService;
import com.example.simplenewschannel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
public class NewsAccessCheckService  implements AccessCheckService {
    private final UserService userService;
    private final NewsRepository newsRepository;

    public NewsAccessCheckService(UserService userService, NewsRepository newsRepository) {
        this.userService = userService;
        this.newsRepository = newsRepository;
    }

    @Override
    public boolean getResultCheck(HttpServletRequest request, Object[] arguments) {
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var requestBody = Arrays.stream(arguments).filter(o -> o instanceof UpsertNewsRequest).findFirst().get();
        String authorName = ((UpsertNewsRequest) requestBody).getAuthorName();
        long userId = userService.findByName(authorName).getId();
        long id = Long.parseLong(pathVariables.get("id"));
        return newsRepository.existsByIdAndAuthorId(id, userId);
    }

    @Override
    public AccessType getType() {
        return AccessType.NEWS;
    }
}
