package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.CommentNews;
import com.example.simplenewschannel.repository.CommentNewsRepository;
import com.example.simplenewschannel.service.CommentNewsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
@Component
public class CommentNewsServiceImpl implements CommentNewsService {
    private final CommentNewsRepository commentNewsRepository;

    public CommentNewsServiceImpl(CommentNewsRepository commentNewsRepository) {
        this.commentNewsRepository = commentNewsRepository;
    }

    @Override
    public List<CommentNews> findAll() {
        return commentNewsRepository.findAll();
    }

    @Override
    public CommentNews findById(long id) {
        return commentNewsRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Комментарий с таким ID {0} не найден ",id))
        );
    }
}
