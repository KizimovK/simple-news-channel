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
        var requestBody = Arrays.stream(arguments).filter(o -> o instanceof UpsertCommentRequest).findFirst().get();
        long userId = ((UpsertCommentRequest) requestBody).getUserId();
        long id = Long.parseLong(pathVariables.get("id"));
        return commentRepository.existsByIdAndUserId(id, userId);
    }

    @Override
    public AccessType getType() {
        return AccessType.COMMENT;
    }
}
