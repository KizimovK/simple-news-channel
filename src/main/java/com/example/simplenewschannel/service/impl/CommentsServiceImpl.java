package com.example.simplenewschannel.service.impl;

import com.example.simplenewschannel.entity.Comment;
import com.example.simplenewschannel.entity.News;
import com.example.simplenewschannel.entity.User;
import com.example.simplenewschannel.repository.CommentRepository;
import com.example.simplenewschannel.service.CommentsService;
import com.example.simplenewschannel.service.NewsService;
import com.example.simplenewschannel.service.UserService;
import com.example.simplenewschannel.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
@Service
public class CommentsServiceImpl implements CommentsService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;

    public CommentsServiceImpl(CommentRepository commentRepository, UserService userService, NewsService newsService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.newsService = newsService;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(
                        MessageFormat.format("Комментарий с таким ID {0} не найден ",id))
        );
    }
    @Override
    public Page<Comment> findAllByNewsId(long newsId, Pageable pageable) {
        return commentRepository.findAllByNewsId(newsId, pageable);
    }

    @Override
    @Transactional
    public Comment save(Comment comment, long newsId, long userId) {
        News news = newsService.findById(newsId);
        User user = userService.findById(userId);
        comment.setUser(user);
        comment.setNews(news);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment comment, long id) {
        Comment existedComment = this.findById(id);
        BeanUtils.notNullCopyProperties(comment, existedComment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }


}
