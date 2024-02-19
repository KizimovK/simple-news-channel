package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.repository.CommentNewsRepository;
import com.example.simplenewschannel.service.CommentsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
@Component
public class CommentsServiceImpl implements CommentsService {
    private final CommentNewsRepository commentNewsRepository;

    public CommentsServiceImpl(CommentNewsRepository commentNewsRepository) {
        this.commentNewsRepository = commentNewsRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentNewsRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentNewsRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Комментарий с таким ID {0} не найден ",id))
        );
    }
}
