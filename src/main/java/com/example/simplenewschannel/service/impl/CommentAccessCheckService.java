package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.aop.AccessType;
import com.example.simplenewschannel.dto.request.UpsertCommentRequest;
import com.example.simplenewschannel.repository.CommentRepository;
import com.example.simplenewschannel.service.AbstractAccessCheckService;
import com.example.simplenewschannel.service.AccessCheckService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentAccessCheckService extends AbstractAccessCheckService {
    private final CommentRepository commentRepository;

    @Override
    protected boolean check(long id, long idUser) {
        return commentRepository.existsByIdAndUserId(id, idUser);
    }

    @Override
    public AccessType getType() {
        return AccessType.COMMENT;
    }
}
