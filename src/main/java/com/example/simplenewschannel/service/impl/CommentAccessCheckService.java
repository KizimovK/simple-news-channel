package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.repository.CommentRepository;
import com.example.simplenewschannel.service.AccessCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
public class CommentAccessCheckService implements AccessCheckService {
    private final CommentRepository commentRepository;

    public CommentAccessCheckService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean getResultCheck(HttpServletRequest request, Object[] arguments) {
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = 0;
        String methodHttp = request.getMethod();
        if (methodHttp.equals("PUT")) {
            var requestBody = Arrays.stream(arguments).filter(o -> o instanceof UpsertCommentRequest).findFirst().get();
            userId = ((UpsertCommentRequest) requestBody).getUserId();
        }
        if (methodHttp.equals("DELETE")){
            Map<String, String[]> parameterMap = request.getParameterMap();
            userId = Long.parseLong(Arrays.stream(parameterMap.get("userId")).findFirst().get());
        }
        long id = Long.parseLong(pathVariables.get("id"));
        return commentRepository.existsByIdAndUserId(id, userId);
    }

    @Override
    public AccessType getType() {
        return AccessType.COMMENT;
    }
}
